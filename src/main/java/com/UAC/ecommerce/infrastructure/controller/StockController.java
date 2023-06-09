package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.StockService;
import com.UAC.ecommerce.application.service.ValidateStock;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.Stock;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/products/stock")
public class StockController {
    private final StockService stockService;
    private final ValidateStock validateStock;

    public StockController(StockService stockService, ValidateStock validateStock) {
        this.stockService = stockService;
        this.validateStock = validateStock;
    }

    @GetMapping("/{id}")
    public String show (@PathVariable Long id, Model model){
        Product product = new Product();
        product.setId(id);
        List<Stock> stocks = stockService.getStockByProduct(product);
        model.addAttribute("stocks", stocks);
        model.addAttribute("idproduct",id);
        return  "/admin/stock/show";
    }

    @GetMapping("/create-unit-product/{id}")
    public String create(@PathVariable Long id, Model model){
        model.addAttribute("idproduct", id);
        return "/admin/stock/create";
    }

    @PostMapping("/save-unit-product")
    public String save(Stock stock, @RequestParam("idproduct") Long idproduct, RedirectAttributes redirectAttributes){
        stock.setDateCreated(LocalDateTime.now());
        stock.setDescription(("inventario"));
        Product product = new Product();
        product.setId(idproduct);
        stock.setProduct(product);
        stockService.saveStock(validateStock.calculateBalance(stock));
        redirectAttributes.addFlashAttribute("mensaje", "Acción realizada con éxito")
                .addFlashAttribute("clase", "success");

        return "redirect:/admin/products/show";
    }

}
