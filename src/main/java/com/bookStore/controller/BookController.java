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
	import org.springframework.transaction.annotation.Transactional;
	
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

		@Transactional
		@RequestMapping(value = "/mylist/{id}", method = RequestMethod.POST)
		public String getMyList(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
			// Получаем текущего пользователя
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			PersonDetails currentUserDetails = (PersonDetails) authentication.getPrincipal();
			Person currentUser = currentUserDetails.getPerson();

			Book b = service.getBookById(id);

			// Проверяем, что количество запрошенных книг меньше или равно доступному количеству
			if (quantity > b.getQuantity() || quantity <= 0) {
				// Обработка ошибки или вывод сообщения пользователю
				// Например, можно выбросить исключение или отобразить сообщение пользователю и
				// перенаправить его обратно на страницу с доступными книгами.
				return "redirect:/available_books";
			}

			// Создаем экземпляр MyBookList для текущего пользователя
			MyBookList mb = new MyBookList(currentUser.getId(), b.getId(), b.getName(), b.getAuthor(), b.getPrice(),
					quantity);
			myBookService.saveMyBooks(mb);

			// Уменьшаем количество книг в наличии на выбранное количество
			b.setQuantity(b.getQuantity() - quantity);
			service.save(b);

			return "redirect:/my_books";
		}

		@PostMapping("/update_book")
		public String updatePerson(@ModelAttribute Book updatedBook) {
			int id = updatedBook.getId();
			service.update(id, updatedBook);
			return "redirect:/available_books";
		}
		@RequestMapping("/editBook/{id}")
		public String editBook(@PathVariable("id") int id, Model model) {
			Book b = service.getBookById(id);
			model.addAttribute("book", b);
			return "bookEdit";
		}

		@RequestMapping("/deleteBook/{id}")
		public String deleteBook(@PathVariable("id") int id) {
			service.deleteById(id);
			return "redirect:/available_books";
		}

		@GetMapping("/book/{id}")
		public String viewBook(@PathVariable("id") int id, Model model) {
			Book book = service.getBookById(id);
			model.addAttribute("book", book);
			return "bookDetails";
		}

	}