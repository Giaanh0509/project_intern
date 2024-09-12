package com.example.bookstore.service;

import com.example.bookstore.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewsService {
    Page<Review> findReviewsByBookId(Integer bookId, Pageable pageable);

    void save(Review review);

    void delete(Integer reviewId);

    Review findById(Integer reviewId);
}
