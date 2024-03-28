package com.bookStore.controller;

import com.bookStore.entity.Book;
import com.bookStore.entity.News;
import com.bookStore.entity.Person;
import com.bookStore.service.BookService;
import com.bookStore.service.NewsService;
import com.bookStore.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminPanelController {
    @Autowired
    private BookService service;
    @Autowired
    private PersonService personService;
    @Autowired
    private NewsService newsService;
    @GetMapping("/admin_panel")
    public String getAdminPanel(){
        return "admin/adminPanel";
    }
    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b) {
        // Проверяем, что количество книг больше нуля
        if (b.getQuantity() <= 0) {
            // Обработка ошибки или вывод сообщения пользователю
            // Например, можно выбросить исключение или отобразить сообщение пользователю и
            // перенаправить его обратно на страницу ввода книги.
            return "redirect:/book_register";
        }
        service.save(b);
        return "redirect:/book_register";
    }
    @GetMapping("/book_register")
    public String bookRegister() {
        return "admin/bookRegister";
    }
    @GetMapping("/all_people")
    public ModelAndView getAllPerson(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Person> personList;
        if (keyword != null && !keyword.isEmpty()) {
            personList = personService.getPersonByEmail(keyword);
        } else {
            personList = personService.findAll();
        }
        return new ModelAndView("admin/allPeople", "person", personList);
    }
    @RequestMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personService.deleteById(id);
        return "redirect:/all_people";
    }
    @RequestMapping("/editPerson/{id}")
    public String showFormEditPerson(@PathVariable("id") int id, Model model) {
        Person p = personService.getPersonById(id);
        model.addAttribute("person", p);
        return "admin/personEdit";
    }

    @PostMapping("/update_person")
    public String updatePerson(@ModelAttribute Person updatedPerson) {
        int id = updatedPerson.getId(); // Получаем ID обновляемого человека
        personService.update(id, updatedPerson); // Вызываем метод update сервиса для обновления информации о человеке
        return "redirect:/all_people"; // Перенаправляем пользователя
    }

    @GetMapping("/sold_books")
    public ModelAndView getSoldBooks(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Book> soldBooks;
        if (keyword != null && !keyword.isEmpty()) {
            // Если указано имя книги, выполняем поиск по этому имени
            soldBooks = service.getBooksByName(keyword);
        } else {
            // В противном случае, просто получаем все проданные книги
            soldBooks = service.getSoldBooks();
        }
        return new ModelAndView("admin/soldBooks", "book", soldBooks);
    }

    @PostMapping("/save_news")
    public String addNews(@ModelAttribute News n) {
        newsService.save(n);
        return "redirect:/news_item";
    }

    @GetMapping("/news_item")
    public String showFormNewsRegister() {
        return "admin/newsItem";
    }

    @RequestMapping("/deleteNews/{id}")
    public String deleteNews(@PathVariable("id") int id) {
        newsService.deleteById(id);
        return "redirect:/";
    }
    @RequestMapping("/editNews/{id}")
    public String showFormEditNews(@PathVariable("id") int id, Model model) {
        News n = newsService.getNewsById(id);
        model.addAttribute("news", n);
        return "admin/newsEdit";
    }
    @PostMapping("/update_news")
    public String updateNews(@ModelAttribute News updatedNews) {
        int id = updatedNews.getId();
        newsService.update(id, updatedNews);
        return "redirect:/";
    }
    @GetMapping("/news/{id}")
    public String viewNews(@PathVariable("id") int id, Model model) {
        News news = newsService.getNewsById(id);
        model.addAttribute("news", news);
        return "admin/newsDetails";
    }
}
