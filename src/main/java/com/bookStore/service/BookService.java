package com.bookStore.service;

import java.util.List;

import com.bookStore.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookStore.entity.Book;
import com.bookStore.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bRepo;
	
	public void save(Book b) {
		bRepo.save(b);
	}
	
	public List<Book> getAllBook(){
		return bRepo.findAll();
	}
	
	public Book getBookById(int id) {
		return bRepo.findById(id).get();
	}
	public void deleteById(int id) {
		bRepo.deleteById(id);
	}
	public List<Book> getBooksByName(String name){
		return bRepo.findByName(name);
	}
	public void update(int id, Book updateBook){
		updateBook.setId(id);
		bRepo.save(updateBook);
	}
	public List<Book> getSoldBooks(){
		return bRepo.findSoldBooks();
	}

	public List<Book> getSoldQuantityGreaterThan(int quantity){
		return bRepo.findBySoldQuantityGreaterThan(quantity);
	}
}
