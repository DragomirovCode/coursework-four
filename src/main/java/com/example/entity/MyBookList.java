package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="MyBooks")
public class MyBookList {
	@Id
	private String id;

	@NotEmpty(message = "Название книги не должно быть пустым")
	@Column(name = "name")
	private String name;

	@NotEmpty(message = "Имя автора не должно быть пустым")
	@Column(name = "author")
	private String author;

	@PositiveOrZero(message = "Цена должна быть неотрицательной")
	@Column(name = "price")
	private int price;

	@PositiveOrZero(message = "Количество книг должно быть неотрицательным")
	@Column(name = "quantity")
	private int quantity; // количество книг в наличии

	private int currentUserId;

	private LocalDateTime timestamp;
	public MyBookList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyBookList(String id, String name, String author, int price, int quantity) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.price = price;
		this.quantity = quantity;
	}
}
