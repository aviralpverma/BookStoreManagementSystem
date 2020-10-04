package utils;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.time.LocalDate;
import static java.time.LocalDate.parse;

import com.app.core.Admin;
import com.app.core.Book;
import com.app.core.Customer;
import com.app.core.User;

import custom_exceptions.InvalidInputException;

public class Validations {
	
	//User validations ---->
	public static final LocalDate chkDate = parse("1920-01-01");
	
	public static String validateEmail(String email) throws InvalidInputException {
		if (!email.contains("@") || !email.endsWith(".com"))
			throw new InvalidInputException("Invalid email address");
		return email;
	}
	
	public static String validateSignUpEmail(String email, HashMap<String, User> usersInDB) throws InvalidInputException {
		User chkUserByEmail = usersInDB.get(email);
		if(chkUserByEmail != null) {
			throw new InvalidInputException("A user with this email already exists!");
		}
		return email;
	}
	
	public static User validateUserByEmail(String email, HashMap<String, User> usersInDB) throws InvalidInputException {
		User oneUser = usersInDB.get(email);
		if(oneUser == null) {
			throw new InvalidInputException("No user exits with this email!");
		}
		return oneUser;
	}

	public static String validatePassword(String pswrd) throws InvalidInputException {
		if (pswrd.length() < 8) {
			throw new InvalidInputException("Password should not be less than 8 characters long");
		}
		return pswrd;
	}
	
	public static Admin.AdminType validateAdminType(String type) {
		return Admin.AdminType.valueOf(type);
	}
	
	public static long validatePhnNumber(String phnNumber) throws InvalidInputException {
		Long pn = 0L;
		if (phnNumber.length() != 10) {
			throw new InvalidInputException("Phone Number should be 10 digits long");
		}
		pn = Long.parseLong(phnNumber);
		return pn;
	}
	
	public static LocalDate inputAndValidateDoB(String dob) throws DateTimeParseException, InvalidInputException {
		LocalDate DoB = parse(dob);
		if(DoB.isBefore(chkDate)) {
			throw new InvalidInputException("Please enter a date of birth after 1920-01-01");
		}
		return DoB;
	}
	
	public static User validateLogin(HashMap<String, User> users, String email, String password) throws InvalidInputException {
		User user = users.get(email);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				if (user instanceof Admin) {
					System.out.println("Login Successful, Hello Admin!");
//					System.out.println("\nAll customer data...");
//					for(User u : users.values()) {
//						System.out.println(u);
//					}
					return user;
					
				} else if (user instanceof Customer) {
					System.out.println("Login Successful, Hello " + ((Customer) user).getName() + "!");
					return user;
				}
			} else {
				throw new InvalidInputException("Incorrect Password");
			}
		} else {
			throw new InvalidInputException("No User exists with this email");
		}
		return user;
	}
	
	public static void chkSuperAdminLogin(User user) throws InvalidInputException {
		if(!((Admin) user).getAdminType().toString().equals("SUPER")) {
			throw new InvalidInputException("You dont have the required permissions for this operation!");
		}
	}
	
	public static void checkUserLogin(boolean userHasLoggedIn) throws InvalidInputException {
		if(!userHasLoggedIn) {
			throw new InvalidInputException("Please Login first!");
		}
	}
	
	public static void isUserAlreadyLoggedIn(boolean userHasLoggedIn) throws InvalidInputException {
		if(userHasLoggedIn) {
			throw new InvalidInputException("You are already logged in!");
		}
	}
	
	//Book Validations ---->
	public static Book.Category validateBookCategory(String genre) {
		return Book.Category.valueOf(genre);
	}
	
	public static double validatePrice(double price) throws InvalidInputException {
		if(price < 1) {
			throw new InvalidInputException("Price cannot be negative");
		}
		return price;
	}
	
	public static int validateQuantity(int quantity) throws InvalidInputException {
		if(quantity < 1) {
			throw new InvalidInputException("Quantity cannot be less than one");
		}
		return quantity;
	}
	
	public static double validateRating(double rating) throws InvalidInputException {
		if(rating < 0 || rating > 5) {
			throw new InvalidInputException("Rating should be between 1 and 5");
		}
		return rating;
	}

	public static Book validateBookInStore(HashMap<String, Book> books, String bookTitle) throws InvalidInputException {
		Book book = books.get(bookTitle);
		if(book != null) {
			return book;
		} else {
			throw new InvalidInputException("No book exists in store with this title!");
		}
	}
	
	public static String validateDuplicateBook(HashMap<String, Book> books, String bookTitle) throws InvalidInputException {
		Book book = books.get(bookTitle);
		if(book != null) {
			throw new InvalidInputException("A book already exits in store with this title!");
		}
		return bookTitle;
	}
	
	//Cart Validations ------>
	public static void checkCartState(HashMap<String, Book> cart) throws InvalidInputException {
		if(cart.isEmpty()) {
			throw new InvalidInputException("Cart is Empty!");
		}
	}
	
	public static Book validateBookInCart(HashMap<String, Book> cart, String bookTitle) throws InvalidInputException {
		Book book = cart.get(bookTitle);
		if(book != null) {
			return book;
		} else {
			throw new InvalidInputException("No book exits in cart with this title!");
		}
	}
}
