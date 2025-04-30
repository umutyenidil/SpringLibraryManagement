package com.umutyenidil.librarymanagement.translator;

import com.umutyenidil.librarymanagement._core.entity.SoftDeletableEntity;
import com.umutyenidil.librarymanagement.book.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Translator extends SoftDeletableEntity {
    private String name;
    private String surname;

    // todo: nationality eklenebilir
    // private Nationality nationality;

    @ManyToMany(mappedBy = "translators")
    private List<Book> books;
}
