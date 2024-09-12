package com.example.bookstore.controller.admin;

import com.example.bookstore.dto.BooksOrders;
import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.BooksService;
import com.example.bookstore.service.OrdersDetailsService;
import com.example.bookstore.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller("ordersAdminController")
@RequestMapping("/admin/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersDetailsService ordersDetailsService;

    @Autowired
    private BooksService booksService;

    @RequestMapping()
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Order> orders = ordersService.getAll(pageable);

        model.addAttribute("orders", orders);

        int totalPages = orders.getTotalPages();
        if (totalPages <= 0) {
            return "admin/orders/index";
        }
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);

        return "admin/orders/index";
    }

    @GetMapping("/{id}")
    public String showOrder(@PathVariable("id") Integer id,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            Model model) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(2);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        List<BooksOrders> booksOrdersList = new ArrayList<>();
        booksOrdersList = ordersDetailsService.createBooksOrdersList(booksOrdersList, id);
        Page<BooksOrders> booksOrders = ordersDetailsService.convertListToPage(booksOrdersList, pageable);
        User user = ordersService.findUserByOrderId(id);

        model.addAttribute("booksOrders", booksOrders);
        model.addAttribute("orderId", id);
        model.addAttribute("user", user);

        int totalPages = booksOrders.getTotalPages();
        if (totalPages <= 0) {
            return "admin/orders/details";
        }
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pageNumbers", pageNumbers);

        boolean checkBookStatusInOrder= ordersDetailsService.checkOrder(booksOrdersList);
        model.addAttribute("checkBookStatusInOrder", checkBookStatusInOrder);
        return "admin/orders/details";
    }

    @GetMapping("/{orderId}/book/{id}")
    public String removeBookInOrder(@PathVariable("orderId") Integer orderId, @PathVariable("id") Integer id, Model model) {
        ordersDetailsService.RemoveBookFromOrder(id);
        return "redirect:/admin/orders/" + orderId;
    }

    @GetMapping("/{orderId}/confirmation")
    public String confirmOrders(@PathVariable("orderId") Integer orderId, Model model) {
        String notification;
        notification = "The products are in sufficient quantity in stock, are you sure you want to accept";
        model.addAttribute("notification", notification);
        model.addAttribute("orderId", orderId);
        return "admin/orders/confirmation";
    }

    @GetMapping("/{orderId}/accept")
    public String acceptOrders(@PathVariable("orderId") Integer orderId, Model model) {
        List<BooksOrders> booksOrdersList = new ArrayList<>();
        booksOrdersList = ordersDetailsService.createBooksOrdersList(booksOrdersList, orderId);
        for (BooksOrders booksOrders: booksOrdersList) {
            booksService.updateBookQuantity(booksOrders.getBook().getId(), booksOrders.getOrderQuantity());
        }
        ordersService.acceptOrders(orderId);
        return "redirect:/admin/orders";
    }

    @GetMapping("/{orderId}/reject")
    public String rejectOrders(@PathVariable("orderId") Integer orderId, Model model) {
        ordersService.rejectOrders(orderId);
        return "redirect:/admin/orders";
    }
}