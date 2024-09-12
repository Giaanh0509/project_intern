package com.example.bookstore.controller.admin;

import com.example.bookstore.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/revenues")
public class RevenuesController {
    @Autowired
    private OrdersService ordersService;

    @RequestMapping()
    public String allRevenue(Model model) {
        double totalPrice = ordersService.totalRevenue();
        int orderCompletedQuantity = ordersService.countOrdersByStatusCompleted();
        int booksSoldQuantity = ordersService.countBooksSold();
        List<Integer> recentOrderMonths = ordersService.findRecentOrderMonths();

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("orderCompletedQuantity", orderCompletedQuantity);
        model.addAttribute("booksSoldQuantity", booksSoldQuantity);
        model.addAttribute("recentOrderMonths", recentOrderMonths);
        return "admin/revenue/index";
    }

    @RequestMapping("/{month}")
    public String monthlyRevenue(@PathVariable("month") Integer month, Model model) {
        List<Integer> recentOrderMonths = ordersService.findRecentOrderMonths();
        List<Integer> weekly = new ArrayList<>();
        for (int i=1; i<5; i++) {
            weekly.add(i);
        }
        System.out.println(month);

        double totalPrice=0;
        if(ordersService.findTotalPriceByMonth(month) != null) {
            totalPrice = ordersService.findTotalPriceByMonth(month);
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("orderCompletedQuantity", ordersService.countCompletedOrdersByMonth(month));
        model.addAttribute("month", month);
        model.addAttribute("recentOrderMonths", recentOrderMonths);
        model.addAttribute("weekly", weekly);
        return "admin/revenue/monthly";
    }
}
