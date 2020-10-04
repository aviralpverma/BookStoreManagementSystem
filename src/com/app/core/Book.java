package com.app.core;

import java.time.LocalDate;
import java.util.Arrays;

public class Book {
	private String title;
	private Category genre;
	private double price;
	private LocalDate publishDate;
	private int quantity;
	private double rating;
	private Author[] author;

	public enum Category {
		FICTION,SCIENCE,FINANCE,YOGA,MEDITATION;
		
		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	public Book(String title, Category genre, double price, LocalDate publishDate, int quantity, double rating, Author... authors) {
		this.title = title;
		this.genre = genre;
		this.price = price;
		this.publishDate = publishDate;
		this.quantity = quantity;
		this.rating = rating;
		this.author = authors;
	}
	
	public Book(String title, double price, int quantity) {
		this.title = title;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String showBookInCart() {
		return "Title : "+title+", Price : "+price+", Quantity : "+quantity;
	}
	
	public void setAuthor(Author... author) {
		this.author = author;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getPrice() {
		return price;
	}
	
	public Category getGenre() {
		return genre;
	}

	@Override
	public String toString() {
		return "\nTITLE : " + title + ", AUTHOR(s) : " + Arrays.toString(author) + ", GENRE : " + genre + ", PRICE : " + price
				+ ", PUBLISH DATE : " + publishDate + ", STOCK QUANTITY : " + quantity + ", RATING : " + rating;
	}
}
