package com.example.bookstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookstore.entity.Cart;

@Repository
public interface CartsRepository extends JpaRepository<Cart, Integer> {
	Cart findByUserId(int userId);
}