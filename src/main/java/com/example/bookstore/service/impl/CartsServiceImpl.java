package com.example.bookstore.service.impl;

import java.util.Collections;
import java.util.List;

import com.example.bookstore.dao.BooksRepository;
import com.example.bookstore.dao.UsersRepository;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.CartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookstore.dao.CartDetailsRepository;
import com.example.bookstore.dao.CartsRepository;
import com.example.bookstore.entity.Cart;
import com.example.bookstore.entity.CartDetail;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartsServiceImpl implements CartsService {
	@Autowired
	private CartsRepository cartsRepository;

	@Autowired
	private CartDetailsRepository cartDetailsRepository;

	@Autowired
	private BooksRepository booksRepository;

	@Autowired
	private UsersRepository usersRepository;

	public List<CartDetail> getCartInfoByUserId(int userId){
	   	Cart cart=cartsRepository.findByUserId(userId);
	   	if(cart !=null) {
	       	return cartDetailsRepository.findByCartId(cart.getId());
	   	}
	   	return Collections.emptyList();
	}
	
	@Override
	public void updateCartItem(int cartDetailId, int quantity) {
		CartDetail cartDetail = cartDetailsRepository.findById(cartDetailId).orElse(null);
		if (cartDetail != null && quantity > 0) {
			cartDetail.setQuantity(quantity);
			cartDetailsRepository.save(cartDetail);
		}
	}

	@Transactional
	@Override
	public void removeCartItem(int cartDetailId) {
		cartDetailsRepository.deleteCartDetailById(cartDetailId);
	}

	@Override
	public CartDetail findCartDetailById(int cartDetailId) {
		return cartDetailsRepository.findById(cartDetailId).orElse(null);
	}

	@Override
	public void addCartItem(int userId, int bookId, int quantity) {
		 Cart cart = cartsRepository.findByUserId(userId);
		 User user = usersRepository.findById(userId);
	     if (cart == null) {
	         cart = new Cart();
	         cart.setUser(user);
	         cart = cartsRepository.save(cart);
	     }
	     Book book = booksRepository.findById(bookId).orElse(null);
	     if (book != null) {
	         CartDetail cartDetail = cartDetailsRepository.findByCartIdAndBookId(cart.getId(), bookId);
	         if (cartDetail == null) {
	             cartDetail = new CartDetail(cart, book);
	             cartDetail.setQuantity(quantity);
	         } else {
	             cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
	         }
	         cartDetailsRepository.save(cartDetail);
	     }
	}
}
