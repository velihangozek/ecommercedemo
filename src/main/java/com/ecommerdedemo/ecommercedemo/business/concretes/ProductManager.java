package com.ecommerdedemo.ecommercedemo.business.concretes;

import com.ecommerdedemo.ecommercedemo.business.abstracts.ProductService;
import com.ecommerdedemo.ecommercedemo.business.dtos.ProductDTO;
import com.ecommerdedemo.ecommercedemo.business.exceptions.ResourceNotFoundException;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Product;
import com.ecommerdedemo.ecommercedemo.repository.abstracts.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ProductManager implements ProductService {
    private final ProductRepository productRepository;

    public ProductManager(ProductRepository productRepository) { // DI
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    @Transactional
    public Product getProductById(Long id) {
//        Optional<Product> optionalProduct = this.productRepository.findById(id);
//        return optionalProduct.get();

        return this.productRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with the related id" + id));

    }

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDiscountRate(productDTO.getDiscountRate());

        Product savedProduct = this.productRepository.save(product);
        return savedProduct;
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = this.productRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with the related id" + id));

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDiscountRate(productDTO.getDiscountRate());

        Product updatedProduct = this.productRepository.save(product);
        return updatedProduct;
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product productToBeDeleted = this.productRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with the related id" + id));

        this.productRepository.delete(productToBeDeleted);
    }
}
