package com.example.bookstore.controller.admin;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Category;
import com.example.bookstore.service.BookCategoriesService;
import com.example.bookstore.service.BooksService;
import com.example.bookstore.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller("adminBooksController")
@RequestMapping("/admin/books")
public class BooksController {
    @Autowired
    private BooksService booksService;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private BookCategoriesService bookCategoriesService;

    @RequestMapping()
    public String index(Model model) {
        List<Book> books = booksService.findAll();
        model.addAttribute("books", books);
        return "admin/books/index";
    }

    @RequestMapping("/new")
    public String newBook(Model model) {
        Book book = new Book();
        List<Category> categories = categoriesService.getAll();
        model.addAttribute("categories", categories);
        model.addAttribute("book", book);
        return "admin/books/form";
    }

    @PostMapping("/")
    public String save(@ModelAttribute("book") Book book,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       @RequestParam("categoryIds") List<Integer> categoryIds,
                       Model model) throws IOException {

        booksService.saveBook(book.getTitle(), book.getPrice(), book.getStockQuantity(), imageFile, categoryIds);
        return "redirect:/admin/books";
    }

    private String encodeFileToBase64(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }
}