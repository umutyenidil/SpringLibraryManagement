package com.umutyenidil.librarymanagement.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<UUID> save(
            @RequestBody @Valid CategoryRequest request
    ) {
        return ResponseEntity.ok(categoryService.saveCategory(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findCategoryById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(categoryService.findCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> findAllCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(categoryService.findAllCategories(PageRequest.of(page - 1, size)));
    }
}
