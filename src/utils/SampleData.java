package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.app.core.Book;
import com.app.core.Customer;
import com.app.core.Admin;
import com.app.core.Author;
import com.app.core.User;

import custom_exceptions.InvalidInputException;

import static utils.Validations.*;

import static java.time.LocalDate.*;

public class SampleData {

	public static HashMap<String, Book> populateBooks() throws InvalidInputException {
		List<Book> booksList = Arrays.asList(
				(new Book("Lord Of The Rings", validateBookCategory("FICTION"), validatePrice(799), of(1991, 4, 22),
						validateQuantity(3), validateRating(4.4),
						(new Author("JRR Tolkien", validateEmail("tolkien@gmail.com"))))),
				
				(new Book("Meditation101", validateBookCategory("MEDITATION"), validatePrice(299), of(2003, 11, 12),
						validateQuantity(5), validateRating(3.9), (new Author("Swara Bhaskar", "leftist@gmail.com")),
						(new Author("Kangana Ranaut", validateEmail("kangana@gmail.com"))))),
				
				(new Book("Harry Potter", validateBookCategory("FICTION"), validatePrice(899), of(2001, 5, 7),
						validateQuantity(3), validateRating(4.5),
						(new Author("JK Rowling", validateEmail("rowling@gmail.com"))))),
				
				(new Book("Yoga101", validateBookCategory("YOGA"), validatePrice(199), of(1991, 04, 22),
						validateQuantity(5), validateRating(4.6), (new Author("Adnan Sami", "adnan@gmail.com")),
						(new Author("Bappi Lahari", validateEmail("bappi@gmail.com"))))),
				
				(new Book("Finance101", validateBookCategory("FINANCE"), validatePrice(1199), of(2007, 1, 1),
						validateQuantity(2), validateRating(3.5), (new Author("Nirmala Seetharaman", validateEmail("fm@gmail.com"))))),
				
				(new Book("A State Of Trance", validateBookCategory("MEDITATION"), validatePrice(599), of(2020, 4, 20),
						validateQuantity(1), validateRating(4.9), (new Author("Armin Van Burren", validateEmail("trippy@gmail.com"))))),
				
				(new Book("Effective Covid Solutions", validateBookCategory("SCIENCE"), validatePrice(99), of(2020, 3, 22),
						validateQuantity(10), validateRating(4.0), (new Author("Donald Trump", validateEmail("trump@gmail.com"))), (new Author("Jair Bolsonaro", validateEmail("bolsonaro@gmail.com"))))),
				
				(new Book("Quantum Computing Intro", validateBookCategory("SCIENCE"), validatePrice(499),
						of(2018, 7, 8), validateQuantity(6), validateRating(3.2))),
				
				(new Book("Power Yoga", validateBookCategory("YOGA"), validatePrice(349), of(2015, 4, 1),
						validateQuantity(5), validateRating(3.8))),
				
				(new Book("Wolf Of Wall Street", validateBookCategory("FINANCE"), validatePrice(299), of(2014, 9, 9),
						validateQuantity(5), validateRating(5.0))));

		HashMap<String, Book> booksMap = new HashMap<String, Book>();

		for (Book b : booksList) {
			booksMap.putIfAbsent(b.getTitle(), b);
		}

		return booksMap;
	}

	public static HashMap<String, User> populateUsers() throws InvalidInputException {
		List<User> usersList = Arrays.asList(
				(new Admin("admin@123.com", "admin123", validateAdminType("SUPER"))),
				(new Customer(validateEmail("spidey123@gmail.com"), validatePassword("iampeter"), "Peter Parker", validatePhnNumber("8254836589"), of(1995, 3, 22))),
				(new Customer(validateEmail("superman456@gmail.com"), validatePassword("kryptoniteislit"), "Clark Kent", validatePhnNumber("9725425474"),of(1880, 11, 12))),
				(new Customer(validateEmail("789batman@gmail.com"), validatePassword("dontcallmebruce"), "Bruce Wayne", validatePhnNumber("6295793768"), of(1981, 4, 7))),
				(new Customer(validateEmail("johnwick321@gmail.com"), validatePassword("mydogisthebest"), "John Wick", validatePhnNumber("1937937690"), of(1978, 1, 14))),
				(new Customer(validateEmail("ironman567@gmail.com"), validatePassword("iamthebest"), "Tony Stark", validatePhnNumber("7207593760"), of(1986, 8, 9))));

		HashMap<String, User> usersMap = new HashMap<String, User>();

		for (User u : usersList) {
			usersMap.putIfAbsent(u.getEmail(), u);
		}

		return usersMap;
	}
}
