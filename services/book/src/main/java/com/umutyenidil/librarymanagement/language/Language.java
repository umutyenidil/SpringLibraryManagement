package com.umutyenidil.librarymanagement.language;

import com.umutyenidil.librarymanagement._core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "languages")
public class Language extends BaseEntity {

    private String name;
}