package com.bookStore.controller;

import com.bookStore.entity.Book;
import com.bookStore.entity.Person;
import com.bookStore.service.BookService;
import com.bookStore.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminPanelController {
    @Autowired
    private BookService service;
    @Autowired
    private PersonService personService;
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
    public ModelAndView getAllPeople() {
        List<Person> list = personService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/allPeople");
        modelAndView.addObject("person", list);
        return modelAndView;
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
}
