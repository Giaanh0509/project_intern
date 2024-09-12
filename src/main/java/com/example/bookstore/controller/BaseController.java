package com.example.bookstore.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.example.bookstore.entity.CartDetail;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.UsersService;

public abstract class BaseController {
	@Autowired
	private UsersService usersService;

	protected User getLoggedInUser(Principal principal, Model model) {
		if (principal == null) {
			model.addAttribute("error", "Bạn chưa đăng nhập");
			return null;
		}
		String username = principal.getName();
		User user = usersService.findByUsername(username);
		if (user == null) {
			model.addAttribute("error", "Người dùng không tồn tại.");
		}
		return user;
	}

	protected boolean checkCartOwnership(User user, CartDetail cartDetail, Model model) {
		if (cartDetail == null) {
			model.addAttribute("error", "Giỏ hàng không tồn tại.");
			return false;
		}
		if (cartDetail.getCart().getUser().getId() != user.getId()) {
			model.addAttribute("error", "Bạn không có quyền chỉnh sửa giỏ hàng này.");
			return false;
		}
		return true;
	}
}