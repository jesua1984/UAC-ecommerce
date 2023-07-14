package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.CategoryService;
import com.UAC.ecommerce.application.service.ProductService;
import com.UAC.ecommerce.application.service.StockService;
import com.UAC.ecommerce.domain.Category;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.Stock;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Controller
@RequestMapping("/home")
@Slf4j
public class HomeController {

    private final ProductService productService;
    private  final StockService stockService;

    private final CategoryService categoryService;

    public HomeController(ProductService productService, StockService stockService, CategoryService categoryService) {
        this.productService = productService;
        this.stockService = stockService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String home(Model model, HttpSession httpSession, @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "20") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> productPage = productService.getProductsPage(page, pageSize);
        Iterable<Category> categories = categoryService.getCategories();

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("categories", categories); // Agrega las categorías al modelo
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());

        try {
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        } catch (Exception e) {
            // Manejar excepción
        }

        return "home";
    }

    @GetMapping("/category-product/{id}")
    public String  showProductCategory(Model model, @PathVariable Long id, @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size){

        Category category = new Category();
        category.setId(id);
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getProductsByCategory(category,pageable);
        Iterable<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories); // Agrega las categorías al modelo
        model.addAttribute("products",productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "home";
    }

    @GetMapping("/index")
    public String index(Model model,HttpSession httpSession, @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int pageSize){
        try {
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        } catch (Exception e) {
            // Manejar excepción
        }

        return "index";
    }

    @GetMapping("/contact")
    public String contact(Model model,HttpSession httpSession){
        try {
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        } catch (Exception e) {
            // Manejar excepción
        }

        return "contact";
    }
    @GetMapping("/privacidad")
    public String privacidad(Model model,HttpSession httpSession){
        try {
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        } catch (Exception e) {
            // Manejar excepción
        }


        return "privacidad";
    }

    @GetMapping("/entregas")
    public String entregas(Model model,HttpSession httpSession){
        try {
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        } catch (Exception e) {
            // Manejar excepción
        }


        return "entregas";
    }

    @GetMapping("/us")
    public String us(Model model,HttpSession httpSession){
        try {
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        } catch (Exception e) {
            // Manejar excepción
        }

        return "us";
    }

    @GetMapping("/product-detail/{id}")
    public String productDetail(@PathVariable Long id, Model model, HttpSession httpSession){

        List<Stock> stocks = stockService.getStockByProduct(productService.getProductById(id));
        if (stocks.size()<=0) {
            Integer lastBalance = 0;
            log.info("balance", lastBalance);
            model.addAttribute("product",productService.getProductById(id));
            model.addAttribute("stock",lastBalance);

        } else {
            Integer lastBalance = stocks.get(stocks.size() - 1).getBalance();
            model.addAttribute("product",productService.getProductById(id));
            model.addAttribute("stock",lastBalance);
        }
        try{
            model.addAttribute("id", httpSession.getAttribute("iduser").toString());
        }catch (Exception e){

        }
        return "user/productdetail";

    }


    @PostMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {

         List<Product> products = productService.findProductsByName(keyword);



        model.addAttribute("products", products);
        model.addAttribute("currentPage", null);  // Reiniciar a la primera página
        model.addAttribute("totalPages", null);   // Reiniciar el número total de páginas
        model.addAttribute("totalItems", products.size());   // Reiniciar el número total de elementos
        return "home";
    }


}
