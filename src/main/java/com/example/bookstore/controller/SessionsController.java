package com.example.bookstore.controller;

import com.example.bookstore.dto.RegistersUser;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.RolesService;
import com.example.bookstore.service.UsersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sessions")
public class SessionsController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "sessions/login";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        RegistersUser registersUser = new RegistersUser();
        model.addAttribute("registerUser", registersUser);
        return "sessions/form";
    }

    @InitBinder
    public void initBinder(WebDataBinder data) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        data.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/")
    public String create(@Valid @ModelAttribute("registerUser") RegistersUser registersUser,
                               BindingResult bindingResult,
                               Model model,
                               HttpSession session) {
        String username = registersUser.getUsername();

        if(bindingResult.hasErrors()) {
            return "sessions/form";
        }

        User checkUser = usersService.findByUsername(username);
        if(checkUser != null) {
            model.addAttribute("registerUser", registersUser);
            model.addAttribute("my_error", "An account using this username already exists");
            return "sessions/form";
        }

        usersService.saveUser(usersService.convertRegisterUserToUser(registersUser));

        session.setAttribute("user", usersService.convertRegisterUserToUser(registersUser));
        return "sessions/login";
    }

}
