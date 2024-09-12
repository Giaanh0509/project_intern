package com.example.bookstore.controller.users;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Category;
import com.example.bookstore.service.BooksService;
import com.example.bookstore.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private BooksService booksService;

    @GetMapping("/home")
    public String home(Model model,
                       @RequestParam(value = "search", required = false) String search,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "6") int size) {
        Page<Book> books;
        List<Book> topRatedBooks = booksService.findTopRatedBooks();
        List<Book> bestSellingBooks = booksService.findBestSellingBooks();

        if (search != null && !search.isEmpty()) {
            books = booksService.findByTitle(search, PageRequest.of(page, size));
        } else {
            books = booksService.findAll(PageRequest.of(page, size));
        }

        model.addAttribute("books", books.getContent());
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        model.addAttribute("topRatedBooks", topRatedBooks);
        model.addAttribute("bestSellingBooks", bestSellingBooks);
        model.addAttribute("defaultTab", bestSellingBooks);

        return "users/home";
    }
}
