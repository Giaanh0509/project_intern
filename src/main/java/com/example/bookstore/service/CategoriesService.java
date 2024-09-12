package com.example.bookstore.service;

import com.example.bookstore.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoriesService {

    Optional<Category> findById(Integer id);

    List<Category> getAll();

    Category getById(Integer id);
}
