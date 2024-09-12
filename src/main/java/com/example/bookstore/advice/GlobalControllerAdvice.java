package com.example.bookstore.advice;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.BooksService;
import com.example.bookstore.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UsersService usersService;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = usersService.findByUsername(username);
            if (user != null) {
                model.addAttribute("userId", user.getId());
            }
        }
    }
}
