package com.example.bookstore.service.impl;

import com.example.bookstore.dao.BookCategoriesRepository;
import com.example.bookstore.dao.CategoriesRepository;
import com.example.bookstore.entity.Book;
import com.example.bookstore.dao.BooksRepository;
import com.example.bookstore.entity.BookCategory;
import com.example.bookstore.entity.Category;
import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class BooksServiceImpl implements BooksService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private BookCategoriesRepository bookCategoriesRepository;

    @Override
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Override
    public void saveBook(String title, double price, int stockQuantity, MultipartFile imageFile, List<Integer> categoryIds) throws IOException {
        try {
            Book book = new Book();
            book.setTitle(title);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            String imageBase64 = encodeFileToBase64(imageFile);
            book.setImage(imageBase64);


            for (Integer categoryId : categoryIds) {
                Category category = categoriesRepository.findById(categoryId).orElse(null);
                if (category != null) {
                    booksRepository.save(book);
                    BookCategory bookCategory = new BookCategory(book, category);
                    bookCategoriesRepository.save(bookCategory);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String encodeFileToBase64(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }

    @Override
    public Page<Book> findByTitle(String title, Pageable pageable) {
        Page<Book> books = booksRepository.findByTitleContainingIgnoreCase(title, pageable);
        if (books.isEmpty()) {
            throw new BookNotFoundException("Không tìm thấy sản phẩm có tên này.");
        }
        return books;
    }

    @Override
    public Page<Book> findByCategoryId(Integer categoryId, Pageable pageable) {
        return booksRepository.findBooksByCategoryId(categoryId, pageable);
    }

    @Override
    public Book findById(Integer id) {
        return booksRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Không tìm thấy sản phẩm."));
    }

    @Override
    public List<Book> findTopRatedBooks() {
        return booksRepository.findTopRatedBooks(PageRequest.of(0, 6)).getContent();
    }

    @Override
    public List<Book> findBestSellingBooks() {
        return booksRepository.findBestSellingBooks(PageRequest.of(0, 6)).getContent();
    }

    @Override
    public void updateBookQuantity(int bookId, int quantity) {
        booksRepository.updateBookStock(bookId, quantity);
    }
}
