package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.Category;
import com.UAC.ecommerce.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepository {
    Iterable<Category> getCategories();

    Category getCategoryById(Long id);

    Category saveCategory(Category category);

    void deleteCategoryById(Long id);

    Page<Category> getCategoriesPage(Pageable pageable);
}
