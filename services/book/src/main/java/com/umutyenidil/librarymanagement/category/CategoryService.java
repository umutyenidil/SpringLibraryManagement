package com.umutyenidil.librarymanagement.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public UUID saveCategory(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name())) throw new CategoryDuplicationException();

        var category = categoryMapper.toCategory(request);

        var savedCategory = categoryRepository.save(category);

        return savedCategory.getId();
    }

    public CategoryResponse findCategoryById(UUID id) {
        return categoryRepository.findByIdAndDeletedAtIsNull(id)
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public Page<CategoryResponse> findAllCategories(Pageable pageable) {
        return categoryRepository.findAllByDeletedAtIsNull(pageable)
                .map(categoryMapper::toCategoryResponse);
    }

    public void deleteCategoryById(UUID id) {
        categoryRepository.findById(id).ifPresent(category -> {
            category.setDeletedAt(LocalDateTime.now());
            categoryRepository.save(category);
        });
    }
}
