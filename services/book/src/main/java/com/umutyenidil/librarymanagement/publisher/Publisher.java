package com.umutyenidil.librarymanagement.publisher;

import com.umutyenidil.librarymanagement._core.entity.SoftDeletableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Publisher extends SoftDeletableEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}