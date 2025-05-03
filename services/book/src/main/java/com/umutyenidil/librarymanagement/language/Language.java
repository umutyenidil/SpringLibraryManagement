package com.umutyenidil.librarymanagement.language;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "languages")
public class Language {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}