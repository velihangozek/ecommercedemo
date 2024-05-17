package com.ecommerdedemo.ecommercedemo.controllers;


import com.ecommerdedemo.ecommercedemo.business.abstracts.CartService;
import com.ecommerdedemo.ecommercedemo.business.dtos.CartDTO;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Cart;
import com.ecommerdedemo.ecommercedemo.entities.concretes.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartsController {

    private CartService cartService;

    public CartsController(CartService cartService) { //DI
        this.cartService = cartService;
    }

    // GET all carts
    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = this.cartService.getAllCarts();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    // GET a cart by ID
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    // POST a new cart
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CartDTO cartDTO) {
        Cart createdCart = cartService.createCart(cartDTO);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    // PUT update a cart
    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody CartDTO cartDTO) {
        Cart updatedCart = cartService.updateCart(id, cartDTO);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    // DELETE a cart by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("{cartId}/items")
    public ResponseEntity<Cart> addToCart(@PathVariable Long cartId, @RequestBody CartItem cartItem) {
        Cart updatedCart = this.cartService.addToCart(cartId, cartItem);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        Cart updatedCart = this.cartService.removeFromCart(cartId, cartItemId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{cartId}/items/{cartItemId}/increase")
    public ResponseEntity<Cart> increaseQuantity(@PathVariable Long cartId, @PathVariable Long cartItemId, @RequestParam int quantity) {
        Cart updatedCart = this.cartService.increaseQuantity(cartId, cartItemId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{cartId}/items/{cartItemId}/decrease")
    public ResponseEntity<Cart> decreaseQuantity(@PathVariable Long cartId, @PathVariable Long cartItemId, @RequestParam int quantity) {
        Cart updatedCart = this.cartService.decreaseQuantity(cartId, cartItemId, quantity);
        return ResponseEntity.ok(updatedCart);
    }


    @GetMapping("/{cartId}/totalPrice")
    public ResponseEntity<Double> calculateTotalPrice(@PathVariable Long cartId) {
        Cart cart = this.cartService.getCartById(cartId);
        double totalPrice = this.cartService.calculateTotalPrice(cart);
        return ResponseEntity.ok(totalPrice);
    }



    // Remarks :

    //@PathVariable Usage: Used for mandatory variables that are part of the URL path. These typically represent a resource identifier or a significant part of the resource's identity.Example: In the URL /carts/{cartId}/items/{cartItemId}, both cartId and cartItemId are essential parts of the path that uniquely identify a specific cart and item within the cart.
    // @RequestParam Usage: Used for optional parameters or query parameters that refine or modify the request. These are usually not essential for identifying the resource but provide additional information for processing the request.Example: In the URL /carts/{cartId}/items/{cartItemId}/increase?quantity=2, the quantity parameter is not part of the path but modifies how the item is processed (e.g., increasing the item's quantity).

    //URL: /carts/{cartId}/items/{cartItemId}/increase?quantity=2
    //@PathVariable Long cartId: cartId is a mandatory part of the path, identifying which cart to modify.
    // @PathVariable Long cartItemId: cartItemId is also a mandatory part of the path, identifying which item in the cart to modify.
    // @RequestParam int quantity: quantity is a query parameter, providing additional information about how the request should be processed (increasing the item's quantity by a certain amount).
}
