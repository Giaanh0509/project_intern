package com.example.bookstore.service.impl;

import com.example.bookstore.dao.BookCategoriesRepository;
import com.example.bookstore.entity.BookCategory;
import com.example.bookstore.service.BookCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCategoriesServiceImpl implements BookCategoriesService {

    @Autowired
    private BookCategoriesRepository bookCategoriesRepository;

    @Override
    public void save(BookCategory bookCategory) {
        bookCategoriesRepository.save(bookCategory);
    }
}
