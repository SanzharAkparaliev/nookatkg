package com.example.nookatkg.service.impl;

import com.example.nookatkg.model.Category;
import com.example.nookatkg.repo.CategoryRepository;
import com.example.nookatkg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void createCategory(Category category) {
         categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category, Long categoryId) {
        Category categoryinDb = categoryRepository.findById(categoryId).get();
        categoryinDb.setName(category.getName());
        categoryRepository.save(categoryinDb);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Optional<Category> getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
