package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {
    Iterable<Product> getProducts();

    Page<Product> getProductsByUser(User user, Pageable pageable);

    Product getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProductById(Long id);


    Page<Product> findAll(Pageable pageable);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategoryContainingIgnoreCase(String category);

}
