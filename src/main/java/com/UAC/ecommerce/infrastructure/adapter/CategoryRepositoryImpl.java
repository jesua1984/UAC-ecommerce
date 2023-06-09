package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.application.repository.CategoryRepository;
import com.UAC.ecommerce.domain.Category;
import com.UAC.ecommerce.infrastructure.mapper.CategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryCrudRepository categoryCrudRepository;
    private final CategoryMapper categoryMapper;


    public CategoryRepositoryImpl(CategoryCrudRepository categoryCrudRepository, CategoryMapper categoryMapper) {
        this.categoryCrudRepository = categoryCrudRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Iterable<Category> getCategories() {
        return categoryMapper.toCategories(categoryCrudRepository.findAll());
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.toCategory(categoryCrudRepository.findById(id).get());
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryMapper.toCategory(categoryCrudRepository.save(categoryMapper.toCategoryEntity(category)));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryCrudRepository.deleteById(id);
    }

    @Override
    public Page<Category> getCategoriesPage(Pageable pageable) {
        return categoryMapper.toCategoryPage(categoryCrudRepository.findAll(pageable));
    }
}
