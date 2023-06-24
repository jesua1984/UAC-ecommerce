package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("user/cart")
@Slf4j
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-product")
    public String addProductCart(@RequestParam Integer quantity, @RequestParam Long idProduct, @RequestParam String nameProduct, @RequestParam BigDecimal price, RedirectAttributes redirectAttributes){
        cartService.addItemCart(quantity,idProduct,nameProduct,price);
        redirectAttributes.addFlashAttribute("success", "Item agregado al carrito");

        showCart();
        return "redirect:/home";
    }

    private void showCart() {
        cartService.getItemCarts().forEach(
                itemCart -> log.info("Item cart: {}",itemCart)
        );
    }

    @GetMapping("/get-cart")
    public String getCart(Model model, HttpSession httpSession){
        showCart();
        model.addAttribute("cart", cartService.getItemCarts());
        model.addAttribute("total", cartService.getTotalCart());
        model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        return "user/cart/cart";
    }

    @GetMapping("/delete-item-cart/{id}")
    public String deleteItemCart(@PathVariable Long id){
        cartService.removeItemCart(id);
        return "redirect:/user/cart/get-cart";
    }
}
