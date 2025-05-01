package com.umutyenidil.librarymanagement.translator;

import com.umutyenidil.librarymanagement.book.Book;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "translators")
public class Translator{
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String surname;

    // todo: nationality eklenebilir
    // private Nationality nationality;

    @ManyToMany(mappedBy = "translators")
    private List<Book> books;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
