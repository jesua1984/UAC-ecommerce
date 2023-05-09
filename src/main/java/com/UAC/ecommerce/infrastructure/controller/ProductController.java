package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.ProductService;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/create")
    public String create(){
        return "/admin/products/create";
    }

    @PostMapping("/save-product")
    public String saveProduct(Product product, @RequestParam("img") MultipartFile multipartFile) throws IOException {
        log.info("Nombre de producto: {}",product);
        productService.saveProduct(product, multipartFile);
        //return "admin/products/create";
        return "redirect:/admin";
    }
    @GetMapping("/show")
    public String showProduct(Model model){
        User user = new User();
        user.setId(1L);
        Iterable<Product> products = productService.getProductsByUser(user);
        model.addAttribute("products",products);
        return "/admin/products/show";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model){
        Product product = productService.getProductById(id);
        log.info("producto obtenido: {}",product);
        model.addAttribute("product", product);
        return "/admin/products/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return "redirect:/admin/products/show";
    }


}
