package com.umutyenidil.librarymanagement.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
