package com.umutyenidil.librarymanagement.book;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "book_copies")
public class BookCopy {
    @Id
    @GeneratedValue
    UUID id;

    private String barcode;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private AcquisitionType acquisitionType;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum AcquisitionType {
        PURCHASED,
        DONATED,
    }

    public enum Condition {
        NEW,
        DAMAGED
    }

    public enum Status {
        AVAILABLE,
        BORROWED
    }

    @PrePersist
    public void prePersist() {
        if (this.barcode == null) {
            this.barcode = "BC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}
