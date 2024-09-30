package com.example.LibraryCatalogSystem.repository;

import com.example.LibraryCatalogSystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b WHERE LOWER(b.author) = LOWER(:author)")
    List<Book> findByAuthor(@Param("author") String author);
    @Query("SELECT b FROM Book b WHERE LOWER(b.title)=LOWER(:title)")
    List<Book> findByTitle(@Param("title") String title);
    @Query("SELECT b FROM Book b WHERE b.available=true")
    List<Book> findBooksByAvailability();

}
