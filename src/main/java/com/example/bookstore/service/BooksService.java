package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BooksService {

    void save(Book book);

    void saveBook(String title, double price, int stockQuantity, MultipartFile imageFile, List<Integer> categoryIds) throws IOException;

    List<Book> findAll();

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByTitle(String title, Pageable pageable);

    Page<Book> findByCategoryId(Integer categoryId, Pageable pageable);

    Book findById(Integer id);

    List<Book> findTopRatedBooks();

    List<Book> findBestSellingBooks();
    void updateBookQuantity(int bookId, int quantity);
}
