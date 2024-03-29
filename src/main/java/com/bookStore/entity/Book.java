package com.bookStore.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "Book")
public class Book {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

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

	@Column(name = "sold_quantity")
	private int soldQuantity; // количество проданных книг

	public Book(String name, String author, int price, int quantity) {
		super();
		this.name = name;
		this.author = author;
		this.price = price;
		this.quantity = quantity;
	}
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(int soldQuantity) {
		this.soldQuantity = soldQuantity;
	}
}
