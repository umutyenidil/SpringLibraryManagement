package com.umutyenidil.librarymanagement.publisher;

import com.umutyenidil.librarymanagement._core.entity.AuditableEntity;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Publisher extends AuditableEntity {
    private String name;
}