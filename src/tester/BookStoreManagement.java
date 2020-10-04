package tester;

import static tester.BookStoreHelper.addBookToInventory;
import static tester.BookStoreHelper.addToCart;
import static tester.BookStoreHelper.checkout;
import static tester.BookStoreHelper.createNewAdmin;
import static tester.BookStoreHelper.removeBookFromInventory;
import static tester.BookStoreHelper.removeFromCart;
import static tester.BookStoreHelper.removeUser;
import static tester.BookStoreHelper.signupNewCustomer;
import static tester.BookStoreHelper.sortAndDisplayBooksByCategory;
import static tester.BookStoreHelper.updateBookQuantity;
import static utils.SampleData.populateBooks;
import static utils.SampleData.populateUsers;
import static utils.Validations.checkCartState;
import static utils.Validations.checkUserLogin;
import static utils.Validations.chkSuperAdminLogin;
import static utils.Validations.isUserAlreadyLoggedIn;
import static utils.Validations.validateBookInCart;
import static utils.Validations.validateBookInStore;
import static utils.Validations.validateEmail;
import static utils.Validations.validateLogin;
import static utils.Validations.validatePassword;
import static utils.Validations.validateQuantity;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.app.core.Admin;
import com.app.core.Book;
import com.app.core.User;

import custom_exceptions.InvalidInputException;

public class BookStoreManagement {
	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {
			HashMap<String, Book> booksMap = new HashMap<>(populateBooks());
			HashMap<String, User> usersMap = new HashMap<>(populateUsers());
			HashMap<String, Book> cart = new HashMap<>();

			boolean exitFlag = false;
			boolean userHasLoggedIn = false;

			System.out.println("\n******Welcome to the BookWorm Book Store*******");
			while (!exitFlag) {
				try {
					System.out.println(
							"\n1.Login " + " \n2.Sign Up " + "\n3.Display All Books in Store " + "\n4.Add book to cart"
									+ "\n5.Show Cart" + "\n6.Remove book from cart" + "\n7.Check out" + "\n10.Exit");
					System.out.println("Enter you choice : ");

					switch (sc.nextInt()) {

					case 1:
						// Login with Admin credentials to see all User data -> email : admin@123.com,
						// password : admin123

						isUserAlreadyLoggedIn(userHasLoggedIn);
						System.out.println("Enter your email and password");
						User user = validateLogin(usersMap, validateEmail(sc.next()), validatePassword(sc.next()));
						userHasLoggedIn = true;
						if (user instanceof Admin) {
							boolean innerExit = false;
							while (!innerExit) {
								try {
									System.out.println(
											"\n1.View All Users \n2.Add an admin user \n3.Remove User"
											+ "\n4.View all books \n5.Add book to inventory \n6.Remove a book from inventory"
											+ "\n7.Update Book Quantity \n10.Exit");
									System.out.println("Enter your choice : ");
									switch (sc.nextInt()) {
									case 1:
										for (User oneUser : usersMap.values()) {
											System.out.println(oneUser);
										}
										break;

									case 2:
										chkSuperAdminLogin(user);
										createNewAdmin(usersMap, sc);
										break;

									case 3:
										chkSuperAdminLogin(user);
										removeUser(usersMap, sc);
										break;
										
									case 4:
										sortAndDisplayBooksByCategory(booksMap);
										break;
									
									case 5:
										chkSuperAdminLogin(user);
										addBookToInventory(booksMap, sc);
										break;
										
									case 6:
										chkSuperAdminLogin(user);
										removeBookFromInventory(booksMap, sc);
										break;
										
									case 7:
										updateBookQuantity(booksMap, sc);
										break;

									case 10:
										userHasLoggedIn = false;
										innerExit = true;
										break;
									}
								} catch (Exception e) {
									System.out.println(e.getMessage());
									System.out.println("In Admin catch");
								}
								sc.nextLine();
							}
						}
						break;

					case 2:
						isUserAlreadyLoggedIn(userHasLoggedIn);
						signupNewCustomer(usersMap, sc);
						break;

					case 3:
						sortAndDisplayBooksByCategory(booksMap);
						break;

					case 4:
						checkUserLogin(userHasLoggedIn);
						System.out.println("\nEnter book title");
						sc.nextLine();
						String bookTitle = sc.nextLine();
						Book book = validateBookInStore(booksMap, bookTitle);
						System.out.println("Enter quantity");
						int quantity = validateQuantity(sc.nextInt());
						addToCart(book, cart, quantity);
						break;

					case 5:
						checkUserLogin(userHasLoggedIn);
						checkCartState(cart);

						for (Book b : cart.values()) {
							System.out.println(b.showBookInCart());
						}
						break;

					case 6:
						checkUserLogin(userHasLoggedIn);
						checkCartState(cart);

						System.out.println("\nEnter book title");
						sc.nextLine();
						bookTitle = sc.nextLine();
						book = validateBookInCart(cart, bookTitle);
						System.out.println("Enter quantity");
						quantity = validateQuantity(sc.nextInt());

						removeFromCart(book, cart, quantity);
						break;

					case 7:
						checkUserLogin(userHasLoggedIn);
						checkCartState(cart);

						checkout(cart, booksMap);
						break;

					case 10:
						exitFlag = true;
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("Please enter valid option in digits!");
					System.out.println("Please try again");
				} catch (InvalidInputException e) {
					System.out.println(e.getMessage());
					System.out.println("In Main catch");
				}
				sc.nextLine();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Exiting ...");
	}
}
