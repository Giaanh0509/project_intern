package com.example.bookstore.controller.users;

import com.example.bookstore.entity.User;
import com.example.bookstore.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/account")
    public String showPage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = usersService.findByUsername(userDetails.getUsername());

        model.addAttribute("user", user);
        return "users/user/show";
    }

    @PostMapping("/account")
    public String update(
            @RequestParam("username") String username,
            @RequestParam("address") String address,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam(value = "currentPassword", required = false) String currentPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            Model model
    ) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = usersService.findByUsername(userDetails.getUsername());

        user.setUsername(username);
        user.setAddress(address);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        if (newPassword != null && !newPassword.isEmpty()) {
            if (newPassword.equals(confirmPassword)) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(newPassword));
                } else {
                    model.addAttribute("error", "Sai mật khẩu.");
                    model.addAttribute("user", user);
                    return "users/user/show";
                }
            } else {
                model.addAttribute("error", "Mật khẩu mới không chính xác.");
                model.addAttribute("user", user);
                return "users/user/show";
            }
        }

        usersService.saveUser(user);
        return "redirect:/sessions/login";
    }
}
