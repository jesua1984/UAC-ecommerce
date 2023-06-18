package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.repository.ProductRepository;
import com.UAC.ecommerce.application.service.CategoryService;
import com.UAC.ecommerce.application.service.ProductService;
import com.UAC.ecommerce.domain.Category;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.Stock;
import com.UAC.ecommerce.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;

    private final CategoryService categoryService;


    public ProductController(ProductService productService, ProductRepository productRepository, CategoryService categoryService) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @GetMapping("/create")
    public String create(Model model){
        showCategory(model);
        return "/admin/products/create";
    }

    @PostMapping("/save-product")
    public String saveProduct(Product product, @RequestParam("img") MultipartFile multipartFile, HttpSession httpSession, RedirectAttributes redirectAttributes) throws IOException {
        log.info("Nombre de producto: {}",product);
        productService.saveProduct(product, multipartFile, httpSession);
        //return "admin/products/create";
        redirectAttributes.addFlashAttribute("mensaje", "Acción realizada con éxito")
                .addFlashAttribute("clase", "success");
        return "redirect:/admin/products/show";
    }
    @GetMapping("/show")
    public String showProduct(HttpSession httpSession, @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model){
        log.info("id user desde la variable de sesion: {}",httpSession.getAttribute("iduser").toString());

        User user = new User();
        user.setId(Long.valueOf(httpSession.getAttribute("iduser").toString()));
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getProductsByUser(user,pageable);
        model.addAttribute("products",productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "/admin/products/show";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model){
        showCategory(model);
        Product product = productService.getProductById(id);
        log.info("producto obtenido: {}",product);
        model.addAttribute("product", product);
        return "/admin/products/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes){
        try{
            productService.deleteProductById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Producto Eliminado correctamente")
                    .addFlashAttribute("clase", "success");
            return "redirect:/admin/products/show";

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje", "No es posible Eliminar, existen órdenes creadas con el producto")
                    .addFlashAttribute("clase", "danger");
            return "redirect:/admin/products/show";

        }

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

    public void showCategory(Model model){
        Iterable<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
    }



}
