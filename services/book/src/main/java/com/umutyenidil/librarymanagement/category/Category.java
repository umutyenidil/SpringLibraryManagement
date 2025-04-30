package com.umutyenidil.librarymanagement.category;

import com.umutyenidil.librarymanagement._core.entity.SoftDeletableEntity;
import com.umutyenidil.librarymanagement.book.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Category extends SoftDeletableEntity {
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Book> books;
}
