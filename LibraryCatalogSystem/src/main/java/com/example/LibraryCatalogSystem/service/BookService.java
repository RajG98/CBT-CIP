package com.example.LibraryCatalogSystem.service;

import com.example.LibraryCatalogSystem.model.Book;
import com.example.LibraryCatalogSystem.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepo repo;

    public List<Book> getBooks(){
        return repo.findBooksByAvailability();
    }

    public List<Book> getBooksByAuthorName(String author) {
        return repo.findByAuthor(author);
    }

    public List<Book> getBooksByTitle(String title) {
        return repo.findByTitle(title);
    }

    public void addBook(Book book) {
        repo.save(book);
    }
}
