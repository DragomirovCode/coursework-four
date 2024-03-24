package com.bookStore.controller;

import com.bookStore.entity.Person;
import com.bookStore.security.PersonDetails;
import com.bookStore.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.*;

@Controller
public class BookController {
	
	@Autowired
	private BookService service;
	
	@Autowired
	private MyBookListService myBookService;

	@Autowired
	private PersonService personService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/book_register")
	public String bookRegister() {
		return "bookRegister";
	}

	@GetMapping("/available_books")
	public ModelAndView getAllBook(@RequestParam(value = "keyword", required = false) String keyword) {
		List<Book> list;
		if (keyword != null && !keyword.isEmpty()) {
			list = service.getBooksByName(keyword);
		} else {
			list = service.getAllBook();
		}
		return new ModelAndView("bookList", "book", list);
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
		return "redirect:/available_books";
	}

	@GetMapping("/my_books")
	public String getMyBooks(Model model, Principal principal, @RequestParam(value = "keyword", required = false) String keyword) {
		String username = principal.getName();
		Optional<Person> person = personService.getPersonByName(username);
		List<MyBookList> list;
		if (keyword != null && !keyword.isEmpty()) {
			list = myBookService.getBooksByUserIdAndName(person.get().getId(), keyword);
		} else {
			list = myBookService.getBooksByUserId(person.get().getId());
		}
		model.addAttribute("book", list);
		return "myBooks";
	}


	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {
		// Получаем текущего пользователя
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails currentUserDetails = (PersonDetails) authentication.getPrincipal();
		Person currentUser = currentUserDetails.getPerson();

		Book b = service.getBookById(id);

		// Создаем экземпляр MyBookList для текущего пользователя
		MyBookList mb = new MyBookList(currentUser.getId(), b.getId(), b.getName(), b.getAuthor(), b.getPrice(),
                b.getQuantity());
		myBookService.saveMyBooks(mb);

		return "redirect:/my_books";
	}
	
	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id,Model model) {
		Book b=service.getBookById(id);
			model.addAttribute("book",b);
		return "bookEdit";
	}
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id")int id) {
		service.deleteById(id);
		return "redirect:/available_books";
	}
	
}
