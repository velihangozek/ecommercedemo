package com.ecommerdedemo.ecommercedemo.business.abstracts;

import com.ecommerdedemo.ecommercedemo.business.dtos.ProductDTO;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProducts();

    public Product getProductById(Long id);

    public Product createProduct(ProductDTO productDTO);

    public Product updateProduct(Long id, ProductDTO productDTO);

    public void deleteProduct(Long id);
}
