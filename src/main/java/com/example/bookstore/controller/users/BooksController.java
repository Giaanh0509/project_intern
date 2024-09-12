package com.example.bookstore.controller.users;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Review;
import com.example.bookstore.service.BooksService;
import com.example.bookstore.service.ReviewsService;
import com.example.bookstore.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksService booksService;

    @Autowired
    private ReviewsService reviewsService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "4") int size,
                       Model model,
                       Principal principal) {

        Book book = booksService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("categories", book.getBookCategories());

        // Pagination for reviews
        Page<Review> reviewsPage = reviewsService.findReviewsByBookId(id, PageRequest.of(page, size));

        model.addAttribute("reviews", reviewsPage.getContent());
        model.addAttribute("totalPages", reviewsPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("loggedInUsername", principal != null ? principal.getName() : null);

        return "users/books/show";
    }
}
