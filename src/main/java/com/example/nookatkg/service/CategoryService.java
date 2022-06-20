package com.example.nookatkg.service;

import com.example.nookatkg.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void createCategory(Category category);
    void updateCategory(Category category,Long categoryId);
    void deleteCategory(Long categoryId);
    Optional<Category> getCategory(Long categoryId);
    List<Category> getCategories();
}
