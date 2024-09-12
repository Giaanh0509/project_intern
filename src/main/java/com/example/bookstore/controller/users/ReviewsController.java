package com.example.bookstore.controller.users;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Review;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.BooksService;
import com.example.bookstore.service.ReviewsService;
import com.example.bookstore.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/books")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @Autowired
    private BooksService booksService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/{id}/reviews")
    public String addReview(@PathVariable Integer id,
                            @RequestParam int rating,
                            @RequestParam String content,
                            Principal principal) {
        if (principal == null) {
            return "redirect:/sessions/login";
        }

        Book book = booksService.findById(id);
        User user = usersService.findByUsername(principal.getName());

        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(rating);
        review.setContent(content);
        reviewsService.save(review);

        return "redirect:/books/" + id;
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewId, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Review review = reviewsService.findById(reviewId);
        String loggedInUsername = principal.getName();

        if (!review.getUser().getUsername().equals(loggedInUsername)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        reviewsService.delete(reviewId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
