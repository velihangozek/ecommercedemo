package com.ecommerdedemo.ecommercedemo.controllers;


import com.ecommerdedemo.ecommercedemo.business.abstracts.HistoricalPriceService;
import com.ecommerdedemo.ecommercedemo.business.abstracts.ProductService;
import com.ecommerdedemo.ecommercedemo.business.dtos.ProductDTO;
import com.ecommerdedemo.ecommercedemo.entities.concretes.HistoricalPrice;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Product;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    //end-point methods..

    private final ProductService productService;
    private final HistoricalPriceService historicalPriceService;


    public ProductsController(ProductService productService, HistoricalPriceService historicalPriceService) {
        this.productService = productService;
        this.historicalPriceService = historicalPriceService;
    }

    // GET all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = this.productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // GET a product by ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = this.productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // POST a new product
    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        Product createdProduct = this.productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // PUT update a product
    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product updatedProduct = this.productService.updateProduct(id, productDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // DELETE a product by ID
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{productId}/historical-prices")
    public ResponseEntity<List<HistoricalPrice>> getHistoricalPricesForRelatedProduct(@PathVariable Long productId) {

        Product product = this.productService.getProductById(productId);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        List<HistoricalPrice> historicalPrices = this.historicalPriceService.getHistoricalPricesForRelatedProduct(product);
        return ResponseEntity.ok(historicalPrices);
    }


}
