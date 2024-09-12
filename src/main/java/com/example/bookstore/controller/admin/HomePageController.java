package com.example.bookstore.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomePageController {
        @RequestMapping()
        public String home() {
              return "admin/layout";
        }
}
