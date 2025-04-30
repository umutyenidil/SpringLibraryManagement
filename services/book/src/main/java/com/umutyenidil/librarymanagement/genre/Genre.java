package com.umutyenidil.librarymanagement.genre;

import com.umutyenidil.librarymanagement._core.entity.SoftDeletableEntity;
import com.umutyenidil.librarymanagement.book.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Genre extends SoftDeletableEntity {
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Book> books;
}
