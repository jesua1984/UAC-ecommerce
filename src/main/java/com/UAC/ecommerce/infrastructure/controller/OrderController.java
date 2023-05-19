package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.*;
import com.UAC.ecommerce.domain.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final StockService stockService;
    private final ValidateStock validateStock;
    private final int UNIT_IN = 0;

    public OrderController(CartService cartService, UserService userService, ProductService productService, OrderService orderService, OrderProductService orderProductService, StockService stockService, ValidateStock validateStock) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
        this.stockService = stockService;
        this.validateStock = validateStock;
    }

    @GetMapping("/sumary-order")
    public String showSumaryOrder(Model model, HttpSession httpSession){
        log.info("id user desde la variable de sesion: {}",httpSession.getAttribute("iduser").toString());
        User user= userService.findById(Long.valueOf(httpSession.getAttribute("iduser").toString()));
        model.addAttribute("cart", cartService.getItemCarts());
        model.addAttribute("total", cartService.getTotalCart());
        model.addAttribute("user", user);
        model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        return "user/sumaryorder";
    }

    @GetMapping("/create-order")
    public String createOrder(RedirectAttributes attributes, HttpSession httpSession){
        log.info("create order..");
        log.info("id user desde la variable de sesion: {}",httpSession.getAttribute("iduser").toString());

        //obtener un usuario temporal
        User user= userService.findById(Long.valueOf(httpSession.getAttribute("iduser").toString()));

        // Verificar si el carrito está vacío
        if (cartService.getItemCarts().isEmpty()) {
            // Carrito vacío, mostrar mensaje de error o redirigir a una página de error
            attributes.addFlashAttribute("message", "El carrito está vacío. No se puede crear la orden.");
            return "redirect:/user/order/sumary-order";
        }

        //order
        Order order= new Order();
        order.setDateCreated(LocalDateTime.now());
        order.setUser(user);

        order = orderService.createOrder(order);


        //order product
        List<OrderProduct> orderProducts = new ArrayList<>();

        //itemcart hacia orderproduct

        for (ItemCart itemCart : cartService.getItemCarts()){
            orderProducts.add(new OrderProduct(productService.getProductById(itemCart.getIdProduct()),itemCart.getQuantity(),order));
        }

        //guardar
        orderProducts.forEach(
                op->{
                    orderProductService.create(op);
                    Stock stock = new Stock();
                    stock.setDateCreated(LocalDateTime.now());
                    stock.setProduct(op.getProduct());
                    stock.setDescription("Venta");
                    stock.setUnitIn(UNIT_IN);
                    stock.setUnitOut(op.getQuantity());
                    stockService.saveStock(validateStock.calculateBalance(stock));
                }
        );
        //vaciar carrito
        cartService.removeAllItemsCart();
        attributes.addFlashAttribute("id", httpSession.getAttribute("iduser").toString());
        return "redirect:/home";
    }
}
