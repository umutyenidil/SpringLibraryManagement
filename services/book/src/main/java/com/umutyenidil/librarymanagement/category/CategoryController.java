package com.umutyenidil.librarymanagement.category;

import com.umutyenidil.librarymanagement.common.dto.response.PageResponse;
import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import com.umutyenidil.librarymanagement.common.util.MessageUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final MessageUtil messageUtil;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<SuccessResponse<UUID>> saveCategory(
            @RequestBody @Valid CategoryRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.of(categoryService.saveCategory(request), messageUtil.getMessage("success.category.create")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<CategoryResponse>> findCategoryById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                SuccessResponse.of(categoryService.findCategoryById(id))
        );
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> findAllCategories(
            @RequestParam(defaultValue = "1") @Positive int page,
            @RequestParam(defaultValue = "20") @Positive int size
    ) {
        return ResponseEntity.ok(
                PageResponse.fromPage(
                        categoryService.findAllCategories(PageRequest.of(page - 1, size))
                                .map(categoryMapper::toCategoryResponse)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(
            @PathVariable UUID id
    ) {
        categoryService.deleteCategoryById(id);

        return ResponseEntity.noContent().build();
    }
}
