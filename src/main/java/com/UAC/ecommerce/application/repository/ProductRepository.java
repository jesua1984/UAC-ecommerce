package com.UAC.ecommerce.application.repository;

import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;

public interface ProductRepository {
    Iterable<Product> getProducts();
    Iterable<Product> getProductsByUser(User user);
    Product getProductById(Long id);
    Product saveProduct(Product product);
    void deleteProductById(Long id);


}
