package com.UAC.ecommerce.application.service;

import com.UAC.ecommerce.application.repository.ProductRepository;
import com.UAC.ecommerce.domain.Product;
import com.UAC.ecommerce.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

public class ProductService {
    private final ProductRepository productRepository;
    private final UploadFile uploadFile;

    public ProductService(ProductRepository productRepository, UploadFile uploadFile) {
        this.productRepository = productRepository;
        this.uploadFile = uploadFile;
    }

    public Iterable<Product> getProducts(){
        return productRepository.getProducts();
    }

    public Iterable<Product> getProductsByUser(User user){
        return productRepository.getProductsByUser(user);
    }

    public Product getProductById(Long id){
        return productRepository.getProductById(id);
    }

    public Product saveProduct(Product product, MultipartFile multipartFile) throws IOException {
        if (Long.valueOf(product.getId())==0){
            User user = new User();
            user.setId(1L);
            product.setDateCreated(LocalDateTime.now());
            product.setDateUpdated(LocalDateTime.now());
            product.setUser(user);
            product.setImage(uploadFile.upload(multipartFile));
            return productRepository.saveProduct(product);
        } else{
            Product productDB=productRepository.getProductById(product.getId());
            //sino se carga la imagen coge la que se guardó al registro
            if (multipartFile.isEmpty()){
                product.setImage(productDB.getImage());
            } else{ //guarda la imagen que se envía actualmente
                //antes se elimina solamente si no es la imagen por defecto
                if (!productDB.getImage().equals("default.jpg")){
                    uploadFile.delete(productDB.getImage());

                }
                product.setImage(uploadFile.upload(multipartFile));
            }
            product.setCode(productDB.getCode());
            product.setUser(productDB.getUser());
            product.setDateCreated(productDB.getDateCreated());
            product.setDateUpdated(LocalDateTime.now());
            return productRepository.saveProduct(product);
        }

    }

    public void deleteProductById(Long id){
        productRepository.deleteProductById(id);

    }


}
