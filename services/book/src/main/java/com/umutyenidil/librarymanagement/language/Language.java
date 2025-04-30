package com.umutyenidil.librarymanagement.language;

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
public class Language {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}