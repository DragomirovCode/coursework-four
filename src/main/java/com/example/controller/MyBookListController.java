package com.example.controller;

import com.example.entity.MyBookList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.MyBookListService;

@Controller
public class MyBookListController {
	
	@Autowired
	private MyBookListService service;
	
	@RequestMapping("/deleteMyList/{id}")
	public String deleteMyList(@PathVariable("id") int id) {
		service.deleteById(id);
		return "redirect:/my_books";
	}

	@GetMapping("/my_book/{id}")
	public String viewMyBook(@PathVariable("id") String id, Model model) {
		MyBookList myBook = service.getMyBookId(id);
		model.addAttribute("myBook", myBook);
		return "book/myBookDetails";
	}
}
