package com.UAC.ecommerce.infrastructure.controller;

import com.UAC.ecommerce.application.service.CategoryService;
import com.UAC.ecommerce.domain.Category;
import com.UAC.ecommerce.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("admin/categories")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/create")
    public String create(){
        return "admin/categories/create";
    }
    @PostMapping("/save-category")
    public String saveCategory(Category category, RedirectAttributes redirectAttributes){
        log.info("Nombre de la categoria: {}",category);
        categoryService.saveCategory(category);
        redirectAttributes.addFlashAttribute("mensaje", "Acción realizada con éxito")
                .addFlashAttribute("clase", "success");
        return "redirect:/admin/categories/show";
    }
    @GetMapping("/show")
    public String showCategory(Model model, @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoriesPage = categoryService.getCategoriesPage(pageable);
        model.addAttribute("categories",categoriesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoriesPage.getTotalPages());
        return "admin/categories/show";
    }
    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model){
        Category category = categoryService.getCategoryById(id);

        model.addAttribute("category", category);
        return "admin/categories/edit";
    }
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes){
        try{
            categoryService.deleteCategoryById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Acción realizada con éxito")
                    .addFlashAttribute("clase", "success");
            return "redirect:admin/categories/show";

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje", "No es posible Eliminar, existen productos creados con la categoría")
                    .addFlashAttribute("clase", "danger");
            return "redirect:/admin/categories/show";
        }

    }


}
