package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.OrderProductService;
import com.UAC.ecommerce.application.service.OrderService;
import com.UAC.ecommerce.application.service.ProductService;
import com.UAC.ecommerce.application.service.UserService;
import com.UAC.ecommerce.domain.Order;
import com.UAC.ecommerce.domain.OrderProduct;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final ProductService productService;

    private final OrderService orderService;

    private final UserService userService;

    private final OrderProductService orderProductService;

    public AdminController(ProductService productService, OrderService orderService, UserService userService, OrderProductService orderProductService) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
        this.orderProductService = orderProductService;
    }

    @GetMapping
    public String home(Model model){
        User user = new User();
        user.setId(1L);
        Iterable<Product> products = productService.getProductsByUser(user);
        model.addAttribute("products",products);
        return "/admin/home";
    }

    @GetMapping("/orders/{id}")
    public String getUserOrders(@PathVariable Long id, Model model) {
        List<Order> newListOrder = new ArrayList<>();
        User user = userService.findById(id);
        Iterable<Order> orders = orderService.getOrdersByUser(user);
        for (Order order: orders){
            newListOrder.add(getOrdersProducts(order));
            log.info("contenido orden: {}",order.getOrderProducts());

        }
        model.addAttribute("id",Long.valueOf(id).toString());
        model.addAttribute("orders", newListOrder);

        return "admin/orders/shoppinglist";
    }

    private Order getOrdersProducts(Order order){
        Iterable<OrderProduct> orderProducts = orderProductService.getOrderProductByOrder(order);
        order.addOrdersProduct((List<OrderProduct>) orderProducts);
        return order;
    }

    @GetMapping("/orders/all")
    public String getAllUserOrders(Model model) {
        List<Order> newListOrder = new ArrayList<>();
        Iterable<Order> orders = orderService.getOrders();
        for (Order order: orders){
            newListOrder.add(getOrdersProducts(order));
        }
        model.addAttribute("orders", newListOrder);

        return "admin/orders/orderdetails";
    }
}
