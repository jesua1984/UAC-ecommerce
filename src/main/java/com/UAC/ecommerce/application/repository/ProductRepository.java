package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {
    Iterable<Product> getProducts();

    Iterable<Product> getProductsByUser(User user);

    Product getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProductById(Long id);


    Page<Product> findAll(Pageable pageable);



}
