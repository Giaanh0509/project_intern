package com.example.bookstore.dao;

import com.example.bookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findById(Integer categoryId);
}
