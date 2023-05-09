package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.ProductService;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String home(Model model){
        User user = new User();
        user.setId(1L);
        Iterable<Product> products = productService.getProductsByUser(user);
        model.addAttribute("products",products);
        return "/admin/home";
    }

}
