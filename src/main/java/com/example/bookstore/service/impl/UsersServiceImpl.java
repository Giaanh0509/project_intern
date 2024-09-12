package com.example.bookstore.service.impl;

import com.example.bookstore.dao.UsersRepository;
import com.example.bookstore.dto.RegistersUser;
import com.example.bookstore.entity.Role;
import com.example.bookstore.service.RolesService;
import com.example.bookstore.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bookstore.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesService rolesService;

    @Override
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = false)
    public User saveUser(User user) {
        return usersRepository.save(user);
    }

    @Override
    public User getById(int id) {
        return usersRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public User convertRegisterUserToUser(RegistersUser registersUser) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setUsername(registersUser.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registersUser.getPassword()));
        user.setEmail(registersUser.getEmail());
        user.setPhoneNumber(registersUser.getPhoneNumber());
        user.setAddress(registersUser.getAddress());
        user.setRole(rolesService.findByName("ROLE_CUSTOMER"));
        return user;
    }

    @Override
    public List<User> getAll() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUser(User user) {
        usersRepository.updateUser(user.getId(), user.getRole());
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(int id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if(user==null) {
            throw new UsernameNotFoundException("Invalid username");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), rolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

	@Override
	public User findById(int useId) {
		Optional<User> user = Optional.ofNullable(usersRepository.findById(useId));
        return user.orElse(null); 
	}
}
