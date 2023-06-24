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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("home")
    public String home(Model model){
        User user = new User();
        user.setId(1L);
        //Iterable<Product> products = productService.getProductsByUser(user);
        //model.addAttribute("products",products);
        return "/admin/home";
    }

    @GetMapping("/orders/{id}")
    public String getUserOrders(@PathVariable Long id, Model model, @RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Order> newListOrder = new ArrayList<>();
        User user = userService.findById(id);
        Page<Order> ordersPage = orderService.getOrdersByUser(user,pageable);
        List<Order> orders = ordersPage.getContent();
        for (Order order : orders) {
            Order orderWithProducts = getOrdersProducts(order);
            newListOrder.add(orderWithProducts);
        }
        model.addAttribute("id",Long.valueOf(id).toString());
        model.addAttribute("orders", newListOrder);
        model.addAttribute("currentPage", ordersPage.getNumber());
        model.addAttribute("pageSize", ordersPage.getSize());
        model.addAttribute("totalPages", ordersPage.getTotalPages());

        return "admin/users/shoppinglist";
    }

    private Order getOrdersProducts(Order order){
        Iterable<OrderProduct> orderProducts = orderProductService.getOrderProductByOrder(order);
        order.addOrdersProduct((List<OrderProduct>) orderProducts);
        return order;
    }

    @GetMapping("/orders/all")
    public String getAllUserOrders(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size, Model model) {
        Pageable pageable = PageRequest.of(page, size);
        List<Order> newListOrder = new ArrayList<>();
        Page<Order> ordersPage = orderService.getOrdersPage(pageable);
        for (Order order: ordersPage){
            newListOrder.add(getOrdersProducts(order));
        }
        //model.addAttribute("orders", newListOrder);

        model.addAttribute("orders", ordersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ordersPage.getTotalPages());

        return "admin/orders/orderdetails";
    }

    @PostMapping("/orders/{orderId}/change-status")
    public String changeOrderStatus(@PathVariable Long orderId, @RequestParam("orderStatus") String status, RedirectAttributes redirectAttributes) {
        // Obtener la orden por su ID
        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            // La orden no existe, mostrar mensaje de error o redirigir a una página de error
            redirectAttributes.addFlashAttribute("mensaje", "La orden no existe")
                    .addFlashAttribute("clase", "success");
            return "redirect:/admin/orders/all";
        }

        // Actualizar el estado de la orden
        order.setOrderStatus(status);
        orderService.updateOrder(order);
        redirectAttributes.addFlashAttribute("mensaje", "Acción realizada con éxito")
                .addFlashAttribute("clase", "success");
        // Redirigir a la página de detalles de la orden
        return "redirect:/admin/orders/all";
    }
}
