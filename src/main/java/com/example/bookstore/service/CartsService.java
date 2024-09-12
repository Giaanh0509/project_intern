package com.example.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookstore.entity.CartDetail;

@Service
public interface CartsService {

	List<CartDetail> getCartInfoByUserId(int userId);
	void updateCartItem(int cartDetailId, int quantity);
	void removeCartItem(int cartDetailId);
	CartDetail findCartDetailById(int cartDetailId);
	void addCartItem(int userId, int bookId, int quantity);
}