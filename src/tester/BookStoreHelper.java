package tester;

import static java.time.LocalDate.parse;
import static utils.Validations.validateBookCategory;
import static utils.Validations.validateBookInStore;
import static utils.Validations.validateDuplicateBook;
import static utils.Validations.validateEmail;
import static utils.Validations.validatePrice;
import static utils.Validations.validateQuantity;
import static utils.Validations.validateRating;
import static utils.Validations.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

import com.app.core.Admin;
import com.app.core.Author;
import com.app.core.Book;
import com.app.core.Book.Category;
import com.app.core.Customer;
import com.app.core.User;

import custom_exceptions.InvalidInputException;

public class BookStoreHelper {
	
	//Contains functionality to support BookStoreManagement
	
	public static void addBookToInventory(HashMap<String, Book> booksStock, Scanner sc) throws InvalidInputException,Exception {

		System.out.println("Enter the name of the book");
		sc.nextLine();
		String title = validateDuplicateBook(booksStock, sc.nextLine());
		System.out.println("Enter the genre");
		Category genre =  validateBookCategory(sc.next());
		System.out.println("Enter the price");
		double price = validatePrice(sc.nextDouble());
		System.out.println("Enter the publishing date");
		LocalDate publishDate = parse(sc.next());
		System.out.println("Enter the quantity");
		int quantity = validateQuantity(sc.nextInt());
		System.out.println("Enter the rating");
		double rating = validateRating(sc.nextInt());
		
		System.out.println("Enter the number of authors you want to add");
		int authNum = sc.nextInt();
		Author[] authors = new Author[authNum];
		for(int i=0; i<authNum; i++) {
			System.out.println("Enter the name");
			sc.nextLine();
			String name = sc.nextLine();
			System.out.println("enter the email");
			String email = validateEmail(sc.next());
			authors[i] = new Author(name, email);
		}
		
		booksStock.putIfAbsent(title, new Book(title, genre, price, publishDate, quantity, rating, authors));
		System.out.println("Book successfully added to inventory!");
	}
	
	public static void removeBookFromInventory(HashMap<String, Book> booksStock, Scanner sc) throws InvalidInputException {

		System.out.println("Enter the book title");
		sc.nextLine();
		String title = sc.nextLine();
		Book bookToRemove = validateBookInStore(booksStock, title);
		booksStock.remove(title, bookToRemove);
		System.out.println("Book successfully removed");
	}
	
	public static void updateBookQuantity(HashMap<String, Book> booksStock, Scanner sc) throws InvalidInputException {
		
		System.out.println("Enter the book title");
		sc.nextLine();
		String title = sc.nextLine();
		Book bookToRemove = validateBookInStore(booksStock, title);
		System.out.println("The quantity of this book in stock is : "+bookToRemove.getQuantity());
		System.out.println("Enter the number(+ or -) to update the quantity with : ");
		int updateQuantity = sc.nextInt();
		int newQuantity = bookToRemove.getQuantity() + updateQuantity;
		if(newQuantity < 1) {
			throw new InvalidInputException("The new quantity must be greater than 1");
		}
		booksStock.get(title).setQuantity(newQuantity);
		System.out.println("Quantity updated successfully");
	}
	
	public static void sortAndDisplayBooksByCategory(HashMap<String, Book> booksStock) {
		
		ArrayList<Book> booksList = new ArrayList<>(booksStock.values());
		
		Collections.sort(booksList, new Comparator<Book>() {
			@Override
			public int compare(Book book1, Book book2) {
				return book1.getGenre().compareTo(book2.getGenre());
			}
		});
		
		for(Book book : booksList) {
			System.out.println(book);
		}
	}
	
	public static void addToCart(Book bookInStore, HashMap<String, Book> cart, int quantity) throws InvalidInputException {

		int diff = bookInStore.getQuantity() - quantity;
		if(diff<0) {
			throw new InvalidInputException("Not enough "+bookInStore.getTitle()+" books in stock!");
		}
		Book bookInCart = cart.get(bookInStore.getTitle());
		if (bookInCart != null) {
			diff = bookInStore.getQuantity() - (bookInCart.getQuantity() + quantity);
			if(diff<0) {
				throw new InvalidInputException("Not enough "+bookInStore.getTitle()+" books in stock!");
			}
			bookInCart.setQuantity(bookInCart.getQuantity() + quantity);
			System.out.println(quantity + " more quantity of this book has been added to cart");
		} else {
			cart.putIfAbsent(bookInStore.getTitle(),new Book(bookInStore.getTitle(), bookInStore.getPrice(), quantity));
			System.out.println("Book with " + quantity + " quantity added to cart");
		}
	}
	
	public static void removeFromCart(Book bookInCart, HashMap<String, Book> cart, int quantity) throws InvalidInputException {
		
		int diff = bookInCart.getQuantity() - quantity;
		if(diff < 0) {
			throw new InvalidInputException("Not enough quantity of this book in cart!");
		} else if(diff == 0) {
			cart.remove(bookInCart.getTitle());
			System.out.println("This book has been removed from cart");
		} else {
			bookInCart.setQuantity(bookInCart.getQuantity() - quantity);
			System.out.println(quantity+" quantity of this book has been removed from cart");
		}
	}
	
	public static void checkout(HashMap<String, Book> cart, HashMap<String, Book> booksStock) throws InvalidInputException {
		
		double TotalPrice = 0;
		
		System.out.println("*****Your Bill******");
		
		for(Book bookInCart : cart.values()) {
			
			Book bookInStore = booksStock.get(bookInCart.getTitle());
			int diff = bookInStore.getQuantity() - bookInCart.getQuantity();
			if(diff==0) {
				bookInStore.setQuantity(diff);
				booksStock.remove(bookInStore.getTitle());
			} else {
				bookInStore.setQuantity(diff);
			}
			
			double linePrice = bookInCart.getPrice() * bookInCart.getQuantity();
			System.out.println(bookInCart.getTitle() +"  X  " + bookInCart.getQuantity() + "  :  " + linePrice);
			
			TotalPrice += linePrice;
		}
		System.out.println("\nTotal Cart Value : Rs."+TotalPrice);
		System.out.println("Thank You for Shopping!");
		cart.clear();
	}
	
	public static void createNewAdmin(HashMap<String, User> users, Scanner sc) throws InvalidInputException,Exception {
		System.out.println("Enter an email");
		String email = validateSignUpEmail(validateEmail(sc.next()), users);
		System.out.println("Enter a password");
		String password = validatePassword(sc.next());
		System.out.println("Enter the Admin Type - SUPER OR NORMAL");
		String AdminType = sc.next();
		users.putIfAbsent(email,
				new Admin(email, password, validateAdminType(AdminType)));
		System.out.println("A " + AdminType + " admin has been created");
	}
	
	public static void removeUser(HashMap<String, User> users, Scanner sc) throws InvalidInputException,Exception {
		System.out.println("Enter the email id of the user");
		String email = sc.next();
		User userToDelete = validateUserByEmail(validateEmail(email), users);
		users.remove(email, userToDelete);
		System.out.println("User has been removed");
	}
	
	public static void signupNewCustomer(HashMap<String, User> users, Scanner sc) throws InvalidInputException,Exception {
		System.out.println("Enter your email");
		String email = validateSignUpEmail(validateEmail(sc.next()), users);
		System.out.println("Enter a password");
		String password = validatePassword(sc.next());
		sc.nextLine();
		System.out.println("Enter you full name with space");
		String name = sc.nextLine();
		System.out.println("Enter your 10 digit Mobile Number");
		long mobNumber = sc.nextLong();
		System.out.println("Enter you Date of Birth");
		LocalDate dateOfBirth = inputAndValidateDoB(sc.next());
		users.putIfAbsent(email, new Customer(email, password, name, mobNumber, dateOfBirth));
		System.out.println("You have signed up successfully, please login now");
	}
}
