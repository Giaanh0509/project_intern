package com.example.bookstore.service.impl;

import com.example.bookstore.dao.CategoriesRepository;
import com.example.bookstore.entity.Category;
import com.example.bookstore.exception.CategoryNotFoundException;
import com.example.bookstore.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServicesImpl implements CategoriesService {

    private CategoriesRepository categoryRepository;

    @Autowired
    public CategoryServicesImpl(CategoriesRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Danh mục không tồn tại.");
        }
        return category;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
