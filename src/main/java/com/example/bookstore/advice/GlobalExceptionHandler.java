package com.example.bookstore.advice;

import com.example.bookstore.exception.BookNotFoundException;
import com.example.bookstore.exception.CategoryNotFoundException;
import com.example.bookstore.exception.OrderNotFoundException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public String handle(BookNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("totalPages", 0);
        return "users/home";
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public String handle(CategoryNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("totalPages", 0);
        return "users/home";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String handle(OrderNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "users/orders/new";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handle404(NoResourceFoundException ex, Model model) {
        return "users/error/404";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handle(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "users/error/500";
    }
}
