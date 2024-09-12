package com.example.bookstore.service.impl;

import com.example.bookstore.dao.RolesRepository;
import com.example.bookstore.entity.Role;
import com.example.bookstore.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesServiceImpl implements RolesService {

    private RolesRepository rolesRepository;

    @Autowired
    public RolesServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Role findByName(String roleName) {
        return rolesRepository.findByName(roleName);
    }
}
