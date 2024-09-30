package com.example.LibraryCatalogSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id",updatable = false,insertable = false)
    private int bookId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Column(name = "publication_year")
    private int publicationYear;
    private double price;
    @Column(name = "quantity_in_stock")
    private int quantity;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    private boolean available=true;
}
