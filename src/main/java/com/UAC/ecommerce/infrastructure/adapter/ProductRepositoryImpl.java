package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.application.repository.ProductRepository;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.infrastructure.entity.ProductEntity;
import com.UAC.ecommerce.infrastructure.mapper.ProductMapper;
import com.UAC.ecommerce.infrastructure.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductCrudRepository productCrudRepository;
    private final ProductMapper  productMapper;
    private final UserMapper userMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public ProductRepositoryImpl(ProductCrudRepository productCrudRepository, ProductMapper productMapper, UserMapper userMapper) {
        this.productCrudRepository = productCrudRepository;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Iterable<Product> getProducts() {
        return productMapper.toProduct(productCrudRepository.findAll());
    }

    @Override
    public Page<Product> getProductsByUser(User user,  Pageable pageable) {
        return productMapper.toProductPage(productCrudRepository.findByUserEntity(userMapper.toUserEntity(user),pageable));
    }

    @Override
    public Product getProductById(Long id) {
        return productMapper.toProduct(productCrudRepository.findById(id).get()) ;
    }

    @Override
    public Product saveProduct(Product product) {

        return productMapper.toProduct(productCrudRepository.save(productMapper.toProductEntity(product)));
    }

    @Override
    public void deleteProductById(Long id) {
        productCrudRepository.deleteById(id);

    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productCrudRepository.findAll(pageable);
    }

    @Override
    public List<Product> findByNameContainingIgnoreCase(String keyword) {
        return (List<Product>) productMapper.toProduct(productCrudRepository.findByNameContainingIgnoreCase(keyword));
    }

    @Override
    public List<Product> findByCategoryContainingIgnoreCase(String category) {
        return (List<Product>) productMapper.toProduct(productCrudRepository.findByCategoryContainingIgnoreCase(category));
    }


}
