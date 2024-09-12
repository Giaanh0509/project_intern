package com.example.bookstore.controller.admin;

import com.example.bookstore.entity.User;
import com.example.bookstore.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("adminUsersController")
@RequestMapping("/admin/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping()
    public String manageUser(Model model) {
        List<User> users = usersService.getAll();
        model.addAttribute("users", users);
        return "admin/users/index";
    }

    @GetMapping("/{id}/edit")
    public String updateUser(@PathVariable("id") Integer id, Model model) {
        User user = usersService.getById(id);
        System.out.println(user.getId());
        model.addAttribute("user", user);
        return "admin/users/edit";
    }

    @PutMapping("/saveupdate")
    public String update(@ModelAttribute("user") User user) {
        usersService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/search")
    public String searchUser(@RequestParam("username") String username, Model model) {
        User users = usersService.findByUsername(username);
        model.addAttribute("users", users);
        model.addAttribute("username", username);
        return "admin/users/index";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        usersService.deleteById(id);
        return "redirect:/admin/users";
    }
}
