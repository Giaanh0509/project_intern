package com.example.bookstore.service.impl;

import com.example.bookstore.dao.BooksRepository;
import com.example.bookstore.dao.ReviewsRepository;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Review;
import com.example.bookstore.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Override
    public Review findById(Integer reviewId) {
        Optional<Review> review = reviewsRepository.findById(reviewId);
        return review.orElse(null);
    }

    @Override
    public Page<Review> findReviewsByBookId(Integer bookId, Pageable pageable) {
        return reviewsRepository.findByBookId(bookId, pageable);
    }

    @Override
    @Transactional
    public void save(Review review) {
        reviewsRepository.save(review);
        updateBookRating(review.getBook());
    }

    @Override
    @Transactional
    public void delete(Integer reviewId) {
        Review review = findById(reviewId);

        if (review != null) {
            reviewsRepository.deleteById(reviewId);
            updateBookRating(review.getBook());
        }
    }

    private void updateBookRating(Book book) {
        List<Review> reviews = reviewsRepository.findByBook(book);

        double avgRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        avgRating = Math.round(avgRating * 10.0) / 10.0;

        book.setRating(avgRating);
        booksRepository.save(book);
    }

}
