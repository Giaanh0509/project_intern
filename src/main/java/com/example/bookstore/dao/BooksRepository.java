package com.example.bookstore.dao;

import com.example.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT b FROM Book b JOIN b.bookCategories bc WHERE bc.category.id = :categoryId")
    Page<Book> findBooksByCategoryId(Integer categoryId, Pageable pageable);

    @Query("SELECT b FROM Book b ORDER BY b.rating DESC")
    Page<Book> findTopRatedBooks(Pageable pageable);

    @Query("SELECT b FROM Book b JOIN OrderDetail od ON b.id = od.book.id GROUP BY b.id ORDER BY COUNT(od.book.id) DESC")
    Page<Book> findBestSellingBooks(Pageable pageable);

    @Modifying
    @Query("UPDATE Book b SET b.stockQuantity = b.stockQuantity - :quantity WHERE b.id = :bookId")
    void updateBookStock(@Param("bookId") int bookId, @Param("quantity") int quantity);
}
