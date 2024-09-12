package com.example.bookstore.service;
import com.example.bookstore.dto.RegistersUser;
import com.example.bookstore.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

import java.util.List;

public interface UsersService extends UserDetailsService {
     User findByUsername(String username);
     User saveUser(User user);
     User getById(int id);
     User convertRegisterUserToUser(RegistersUser registersUser);
     User findById(int useId);
     List<User> getAll();
     void updateUser(User user);
     void deleteById(int id);
}
