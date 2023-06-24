package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.OrderProductService;
import com.UAC.ecommerce.application.service.OrderService;
import com.UAC.ecommerce.application.service.UserService;
import com.UAC.ecommerce.domain.Order;
import com.UAC.ecommerce.domain.OrderProduct;
import com.UAC.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user/cart/shopping")
public class ShoppingListController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderProductService orderProductService;

    public ShoppingListController(OrderService orderService, UserService userService, OrderProductService orderProductService) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderProductService = orderProductService;
    }

    @GetMapping
    public String showShoppingList(Model model, HttpSession httpSession, @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = userService.findById(Long.valueOf(httpSession.getAttribute("iduser").toString()));
        Page<Order> ordersPage = orderService.getOrdersByUser(user, pageable);
        List<Order> orders = ordersPage.getContent();
        List<Order> newListOrder = new ArrayList<>();

        for (Order order : orders) {
            Order orderWithProducts = getOrdersProducts(order);
            newListOrder.add(orderWithProducts);
        }

        model.addAttribute("id", Long.valueOf(httpSession.getAttribute("iduser").toString()));
        model.addAttribute("orders", newListOrder);
        model.addAttribute("currentPage", ordersPage.getNumber());
        model.addAttribute("pageSize", ordersPage.getSize());
        model.addAttribute("totalPages", ordersPage.getTotalPages());

        return "user/shoppinglist";
    }


    private Order getOrdersProducts(Order order){
        Iterable<OrderProduct> orderProducts = orderProductService.getOrderProductByOrder(order);
        order.addOrdersProduct((List<OrderProduct>) orderProducts);
        return order;
    }
}
