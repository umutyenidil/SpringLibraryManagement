package com.umutyenidil.librarymanagement.category;

import com.umutyenidil.librarymanagement.common.exception.ResourceDuplicationException;
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

        // ayni isimli bir kategori varsa hata firlat
        if (categoryRepository.existsByNameIgnoreCase(request.name())) throw new ResourceDuplicationException("error.category.duplicate");

        // request dto'dan entity'e donusum yap
        var category = categoryMapper.toCategory(request);

        // kategoriyi veritabanina kaydet
        var savedCategory = categoryRepository.save(category);

        // kaydedilen kategorinin id'sini dondur
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
