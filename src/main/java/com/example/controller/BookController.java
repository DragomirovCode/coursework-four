	package com.example.controller;
	
	import com.example.entity.News;
	import com.example.entity.Person;
	import com.example.security.PersonDetails;
	import com.example.service.NewsService;
	import com.example.service.PersonService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.validation.BindingResult;
	import org.springframework.web.bind.annotation.*;
	import org.springframework.web.servlet.ModelAndView;
	
	import com.example.entity.Book;
	import com.example.entity.MyBookList;
	import com.example.service.BookService;
	import com.example.service.MyBookListService;
	
	import org.springframework.security.core.Authentication;
	import org.springframework.security.core.context.SecurityContextHolder;
	import org.springframework.transaction.annotation.Transactional;

	import javax.validation.Valid;
	import java.security.Principal;
	import java.time.Instant;
	import java.time.LocalDateTime;
	import java.util.*;

	@Controller
	public class BookController {

		@Autowired
		private BookService service;

		@Autowired
		private MyBookListService myBookService;

		@Autowired
		private PersonService personService;
		@Autowired
		private NewsService newsService;

		@GetMapping("/")
		public ModelAndView getAllNews(@RequestParam(value = "keyword", required = false) String keyword) {
			List<News> list;
			if (keyword != null && !keyword.isEmpty()) {
				list = newsService.getNewsByTitle(keyword);
			} else {
				list = newsService.getAllNews();
			}
			return new ModelAndView("home", "news", list);
		}

		@GetMapping("/available_books")
		public ModelAndView getAllBook(@RequestParam(value = "keyword", required = false) String keyword) {
			List<Book> list;
			if (keyword != null && !keyword.isEmpty()) {
				list = service.getBooksByName(keyword);
			} else {
				list = service.getAllBook();
			}
			return new ModelAndView("book/bookList", "book", list);
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
			return "book/myBooks";
		}

		@Transactional
		@RequestMapping(value = "/mylist/{id}", method = RequestMethod.POST)
		public String getMyList(@PathVariable("id") String id, @RequestParam("quantity") int quantity) {
			// Получаем текущего пользователя
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			PersonDetails currentUserDetails = (PersonDetails) authentication.getPrincipal();
			Person currentUser = currentUserDetails.getPerson();

			Book book = service.getBookById(Integer.parseInt(id));

			// Получаем текущее время в миллисекундах
			long timestamp = Instant.now().toEpochMilli();

			// Создаем уникальный идентификатор, используя ID пользователя и текущее время
			String uniqueId = String.valueOf((currentUser.getId() + timestamp) / 2);

			// Проверяем, что количество запрошенных книг меньше или равно доступному количеству
			if (quantity > book.getQuantity() || quantity <= 0) {
				// Обработка ошибки или вывод сообщения пользователю
				// Например, можно выбросить исключение или отобразить сообщение пользователю и
				// перенаправить его обратно на страницу с доступными книгами.
				return "redirect:/available_products";
			}

			// Создаем экземпляр MyProductList для текущего пользователя
			// Используем этот уникальный идентификатор при создании нового экземпляра MyBookList
			MyBookList myBook = new MyBookList(uniqueId, book.getName(), book.getAuthor(), book.getPrice(), quantity);
			myBook.setCurrentUserId(currentUser.getId());

			myBook.setTimestamp(LocalDateTime.now());
			myBookService.saveMyBooks(myBook);

			// Уменьшаем количество книг в наличии на выбранное количество
			book.setQuantity(book.getQuantity() - quantity);
			// Увеличиваем количество проданных книг на количество продуктов в myBook
			int soldQuantity = book.getSoldQuantity();
			book.setSoldQuantity(soldQuantity + myBook.getQuantity());
			// Сохраняем изменения
			service.save(book);

			return "redirect:/my_books";
		}

		@PostMapping("/update_book")
		public String updateBook(@ModelAttribute @Valid Book updatedBook,
								 BindingResult bindingResult) {
			if (bindingResult.hasErrors()){
				return "book/bookEdit";
			}
			int id = updatedBook.getId();
			service.update(id, updatedBook);
			return "redirect:/available_books";
		}
		@RequestMapping("/editBook/{id}")
		public String editBook(@PathVariable("id") int id, Model model) {
			Book b = service.getBookById(id);
			model.addAttribute("book", b);
			return "book/bookEdit";
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
			return "book/bookDetails";
		}

	}