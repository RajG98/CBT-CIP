package com.example.LibraryCatalogSystem;

import com.example.LibraryCatalogSystem.custom.exception.GlobalException;
import com.example.LibraryCatalogSystem.custom.exception.InvalidDataException;
import com.example.LibraryCatalogSystem.model.Book;
import com.example.LibraryCatalogSystem.model.Genre;
import com.example.LibraryCatalogSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class LibraryCatalogSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryCatalogSystemApplication.class, args);
	}

}
@Component
class ConsoleApp implements CommandLineRunner {

	@Autowired
	BookService service;
	static Scanner sc = new Scanner(System.in);
	@Override
	public void run(String... args) throws Exception{


		
		while (true){
			System.out.println("-----------------------");
			System.out.println("Welcome to the Library Catalog System");
			System.out.println("1. Add a Book");
			System.out.println("2. Search from Books");
			System.out.println("3. List of Books available");
			System.out.println("4. Exit");
			System.out.println("-----------------------");
			String input=sc.nextLine().trim();
			try {
				int choice=Integer.parseInt(input);
				switch(choice){
					case 1:
						addBooks();
						break;
					case 2:
						searchBooks();
						break;
					case 3:
						getBooks();
						break;
					case 4:
						System.exit(0);
						break;
					default:
						System.out.println("Invalid choice! Please try again.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input: " + input);
			}catch (InvalidDataException e) {
				System.out.println("Custom Error: " + e.getMessage());
			} catch (GlobalException e) {
				System.out.println("Global Error: " + e.getMessage());
			}catch (Exception ex){throw new GlobalException("Something went wrong! "+ex.getMessage());}




		}
	}

	private void searchBooks() {
		System.out.println("-----------------------");
		System.out.println("Search from Books");
		System.out.println("1. Search By Title");
		System.out.println("2. Search By Author");
		System.out.println("3. Back to menu");
		System.out.println("4. Exit");
		System.out.println("-----------------------");
		String input=sc.nextLine().trim();
		try {
			int choice=Integer.parseInt(input);
			switch(choice){
				case 1:
					searchByTitle();
					break;
				case 2:
					searchByAuthor();
					break;
				case 3:
					return;

				case 4:
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice! Please try again.");
			}
		} catch (NumberFormatException e) {
			throw new InvalidDataException("Invalid input: " + input);
		}
		catch (Exception ex){throw new GlobalException("Something went wrong! "+ex.getMessage());}

	}

	private void searchByAuthor() {
		System.out.print("Author Name: ");
		String author=sc.nextLine().trim();
		List<Book> books= service.getBooksByAuthorName(author);
		printBooks(books);
	}

	private void printBooks(List<Book> books) {
		if(books.isEmpty()) {
			System.out.println("No Books Found");
			return;
		}

		String[] headers = {"Book ID", "Author", "Genre", "Price", "Publication Year", "Quantity", "Title", "Created At"};

		System.out.printf("%-10s %-20s %-15s %-10s %-16s %-10s %-32s %-20s%n",
				headers[0], headers[1], headers[2], headers[3], headers[4], headers[5], headers[6], headers[7]);

		System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


		for (Book book : books) {

			String createdAtFormatted = (book.getCreatedAt() != null)
					? formatter.format(book.getCreatedAt())
					: "N/A";


			System.out.printf("%-10d %-20s %-15s %-10.2f %-16s %-10d %-32s %-20s%n",
					book.getBookId(),
					book.getAuthor(),
					book.getGenre(),
					book.getPrice(),
					book.getPublicationYear(),
					book.getQuantity(),
					book.getTitle(),
					createdAtFormatted);
		}
	}

	private void searchByTitle() {
		System.out.print("Book Title: ");
		String title=sc.nextLine().trim();

		List<Book> books= service.getBooksByTitle(title);
		printBooks(books);
	}

	private void getBooks() {
		List<Book> books= service.getBooks();
		printBooks(books);
	}
	private void addBooks() {
		try{
		System.out.print("Add Title: ");
		String title=sc.nextLine().trim();
		System.out.print("Add Author: ");
		String author=sc.nextLine().trim();
		System.out.print("Add Genre: ");
		String genre=sc.nextLine().trim().toUpperCase();
		System.out.print("Add Publication Year: ");
		String year=sc.nextLine().trim();
		System.out.print("Add Price: ");
		String price=sc.nextLine().trim();
		System.out.print("Add Quantity: ");
		String qty=sc.nextLine().trim();
		System.out.print("Set Availability(true/false): ");
		String available=sc.nextLine().trim().toLowerCase();

		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		book.setGenre(Genre.valueOf(genre));
		book.setPrice(Double.parseDouble(price));
		book.setQuantity(Integer.parseInt(qty));
		book.setPublicationYear(Integer.parseInt(year));
		if ((Objects.equals(available, "true") || Objects.equals(available, "false"))) {
			book.setAvailable(Boolean.parseBoolean(available));
		}else{
			throw new IllegalArgumentException();
		}
		service.addBook(book);
		System.out.println("Book added to DB!");
		}catch (IllegalArgumentException ex){ throw new InvalidDataException("Invalid Input: "+ex.getMessage());}
		catch (Exception ex){throw new GlobalException("Something went wrong! "+ex.getMessage());}
	}
}