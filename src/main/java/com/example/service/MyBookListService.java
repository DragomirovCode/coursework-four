package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.MyBookList;
import com.example.repository.MyBookRepository;

@Service
public class MyBookListService {
	
	@Autowired
	private MyBookRepository mybook;
	
	public void saveMyBooks(MyBookList book) {
		mybook.save(book);
	}
	
	public List<MyBookList> getAllMyBooks(){
		return mybook.findAll();
	}
	
	public void deleteById(int id) {
		mybook.deleteById(id);
	}

	public List<MyBookList> getBooksByUserId(int userId) {
		return mybook.findByCurrentUserId(userId);
	}

	public List<MyBookList> getBooksByUserIdAndName(int userId, String keyword){
		return mybook.findByCurrentUserIdAndNameContainingIgnoreCase(userId, keyword);
	}

	public MyBookList getMyBookId(String id){
		return mybook.findById(id);
	}
}
