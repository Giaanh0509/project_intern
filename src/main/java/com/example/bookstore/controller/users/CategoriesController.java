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
public class CategoriesController {

    @Autowired
    private BooksService booksService;

    @Autowired
    private CategoriesService categoryService;

    @GetMapping("/categories/{id}")
    public String getBooksByCategory(Model model,
                                     @PathVariable("id") Integer categoryId,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "6") int size) {
        // kiểm tra nếu category id tồn tại
        Optional<Category> optionalCategory = categoryService.findById(categoryId);
        Page<Book> books = booksService.findByCategoryId(categoryId, PageRequest.of(page, size));
        List<Book> topRatedBooks = booksService.findTopRatedBooks();
        List<Book> bestSellingBooks = booksService.findBestSellingBooks();

        model.addAttribute("topRatedBooks", topRatedBooks);
        model.addAttribute("bestSellingBooks", bestSellingBooks);
        model.addAttribute("books", books.getContent());

        if (books.isEmpty()) {
            model.addAttribute("errorMessage", "Danh mục không chứa sản phẩm nào.");
        }

        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("currentPage", page);

        return "users/home";
    }
}
