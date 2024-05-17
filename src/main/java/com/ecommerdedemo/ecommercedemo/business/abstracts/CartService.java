package com.ecommerdedemo.ecommercedemo.business.abstracts;

import com.ecommerdedemo.ecommercedemo.business.dtos.CartDTO;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Cart;
import com.ecommerdedemo.ecommercedemo.entities.concretes.CartItem;

import java.util.List;

public interface CartService {

    public List<Cart> getAllCarts();

    public Cart getCartById(Long id);

    public Cart createCart(CartDTO cartDTO);

    public Cart updateCart(Long id, CartDTO cartDTO);

    public void deleteCart(Long id);

    public Cart addToCart(Long cartId, CartItem cartItem);

    public Cart removeFromCart(Long cartId, Long cartItemId);

    public Cart increaseQuantity(Long cartId, Long cartItemId, int quantity);

    public Cart decreaseQuantity(Long cartId, Long cartItemId, int quantity);

    public double calculateTotalPrice(Cart cart);

}
