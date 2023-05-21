package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.repository.ProductRepository;
import com.UAC.ecommerce.application.service.ProductService;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;


    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/create")
    public String create(){
        return "/admin/products/create";
    }

    @PostMapping("/save-product")
    public String saveProduct(Product product, @RequestParam("img") MultipartFile multipartFile, HttpSession httpSession) throws IOException {
        log.info("Nombre de producto: {}",product);
        productService.saveProduct(product, multipartFile, httpSession);
        //return "admin/products/create";
        return "redirect:/admin/products/show";
    }
    @GetMapping("/show")
    public String showProduct(Model model, HttpSession httpSession){
        log.info("id user desde la variable de sesion: {}",httpSession.getAttribute("iduser").toString());

        User user = new User();
        user.setId(Long.valueOf(httpSession.getAttribute("iduser").toString()));
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

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("keyword") String keyword) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<Product>> getProductsPaged(SpringDataWebProperties.Pageable pageable) {
        Page<Product> products = productRepository.findAll((Pageable) pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
