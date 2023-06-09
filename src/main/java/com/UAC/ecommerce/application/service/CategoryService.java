package com.UAC.ecommerce.application.service;

import com.UAC.ecommerce.application.repository.CategoryRepository;
import com.UAC.ecommerce.domain.Category;
import com.UAC.ecommerce.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Category> getCategories(){
        return categoryRepository.getCategories();
    }

    public Category getCategoryById(Long id){
        return categoryRepository.getCategoryById(id);
    }

    public Category saveCategory(Category category){
        return categoryRepository.saveCategory(category);
    }

    public void deleteCategoryById(Long id){
        categoryRepository.deleteCategoryById(id);

    }

    public Page<Category> getCategoriesPage(Pageable pageable){
        return categoryRepository.getCategoriesPage(pageable);
    }


}
