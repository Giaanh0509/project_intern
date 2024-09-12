package com.example.bookstore.controller.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookstore.entity.CartDetail;
import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.Order.Status;
import com.example.bookstore.entity.OrderDetail;
import com.example.bookstore.entity.User;
import com.example.bookstore.exception.OrderNotFoundException;
import com.example.bookstore.service.CartsService;
import com.example.bookstore.service.OrdersService;
import com.example.bookstore.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private CartsService cartsService;

	@GetMapping("/orders")
	public String index(@RequestParam(value = "status", required = false, defaultValue = "ALL") String selectedStatus,
			HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");

		if (user == null)
			return "redirect:/sessions/login";

		Integer userId = user.getId();
		List<Order> orders;

		if ("ALL".equals(selectedStatus)) {
			orders = ordersService.findOrdersByUserId(userId);
		} else {
			Order.Status status = Order.Status.valueOf(selectedStatus);
			orders = ordersService.findOrdersByUserIdAndStatus(userId, status);
		}

		if (orders.isEmpty()) {
			model.addAttribute("errorMessage", "Chưa có đơn hàng");
		}

		model.addAttribute("orders", orders);
		model.addAttribute("selectedStatus", selectedStatus);

		return "users/orders/index";
	}

	@GetMapping("/orders/new")
	public String newOrder(HttpSession session, Model model) {

		User user = (User) session.getAttribute("user");
		if (user == null)
			return "redirect:/sessions/login";

		Integer userId = user.getId();
		List<CartDetail> cartDetails = cartsService.getCartInfoByUserId(userId);
		if (cartDetails.isEmpty()) {
			throw new OrderNotFoundException("Chưa có sản phẩm nào trong giỏ hàng");
		}
		double totalPrice = cartDetails.stream().mapToDouble(cd -> cd.getBook().getPrice() * cd.getQuantity()).sum();

		Order order = new Order();
		order.setUser(user);
		order.setStatus(Status.PENDING);
		order.setTotalPrice(totalPrice);

		for (CartDetail cartDetail : cartDetails) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setBook(cartDetail.getBook());
			orderDetail.setQuantity(cartDetail.getQuantity());
			orderDetail.setPrice(cartDetail.getBook().getPrice());
			orderDetail.setOrder(order);
			order.getOrderDetails().add(orderDetail);
		}
		session.setAttribute("order", order);
		model.addAttribute("order", order);

		return "users/orders/new";
	}

	@PostMapping("/orders")
	public String placeOrder(HttpSession session, Model model) {

		User user = (User) session.getAttribute("user");
		Order order = (Order) session.getAttribute("order");
		if (order == null) {
			model.addAttribute("errorMessage", "Đơn hàng không tồn tại. Vui lòng thử lại.");
			return "redirect:/order/new";
		}
		order.setOrderDate(new Date());

		ordersService.saveOrderWithDetails(order, new ArrayList<>(order.getOrderDetails()), user);

		session.removeAttribute("order");

		return "users/orders/confirmation";
	}

	@GetMapping("/{orderId}")
	public String show(@PathVariable Integer orderId, Model model) {
		try {
			Order order = ordersService.findOrderByIdWithDetails(orderId);
			model.addAttribute("order", order);
			return "users/orders/detail";
		} catch (OrderNotFoundException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "users/orders/new";
		}

	}
}
