package com.example.bookstore.dao;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewsRepository extends JpaRepository<Review, Integer> {
    Page<Review> findByBookId(Integer bookId, Pageable pageable);

    List<Review> findByBook(Book book);

    Optional<Review> findById(Integer reviewId);

}
