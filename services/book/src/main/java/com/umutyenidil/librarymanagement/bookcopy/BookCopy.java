package com.umutyenidil.librarymanagement.bookcopy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.umutyenidil.librarymanagement.book.Book;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "book_copies")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class BookCopy {

    @Id
    @GeneratedValue
    UUID id;

    @Column(nullable = false)
    private String barcode;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcquisitionType acquisitionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condition condition;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public enum AcquisitionType {
        PURCHASED,
        DONATED;

        @JsonCreator
        public static AcquisitionType fromString(String value) {
            for (AcquisitionType acquisitionType : values()) {
                if (acquisitionType.name().equalsIgnoreCase(value)) {
                    return acquisitionType;
                }
            }
            return null;
        }
    }

    public enum Condition {
        NEW,
        DAMAGED;

        @JsonCreator
        public static Condition fromString(String value) {
            for (Condition condition : values()) {
                if (condition.name().equalsIgnoreCase(value)) {
                    return condition;
                }
            }
            return null;
        }
    }

    @PrePersist
    public void prePersist() {
        if (this.barcode == null) {
            this.barcode = "BC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}
