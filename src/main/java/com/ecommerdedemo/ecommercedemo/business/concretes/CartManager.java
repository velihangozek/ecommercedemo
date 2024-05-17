package com.ecommerdedemo.ecommercedemo.business.concretes;

import com.ecommerdedemo.ecommercedemo.business.abstracts.CartService;
import com.ecommerdedemo.ecommercedemo.business.dtos.CartDTO;
import com.ecommerdedemo.ecommercedemo.business.dtos.CustomerDTO;
import com.ecommerdedemo.ecommercedemo.business.exceptions.ResourceNotFoundException;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Cart;
import com.ecommerdedemo.ecommercedemo.entities.concretes.CartItem;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Customer;
import com.ecommerdedemo.ecommercedemo.repository.abstracts.CartRepository;
import com.ecommerdedemo.ecommercedemo.repository.abstracts.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class CartManager implements CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;

    public CartManager(CartRepository cartRepository, CustomerRepository customerRepository) { // DI
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Cart> getAllCarts() {
        return this.cartRepository.findAll();
    }

    @Override
    public Cart getCartById(Long id) {
        Cart cart = this.cartRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Related Cart with the relevant id cannot be found" + id));
        return cart;
    }

    @Override
    @Transactional
    public Cart createCart(CartDTO cartDTO) {
        Cart cart = new Cart();

        Customer customer = this.customerRepository.findById(cartDTO.getCustomerId()).orElseGet(() -> {
            Customer newCustomer = new Customer();
            newCustomer.setName(cartDTO.getCustomerDTO().getName());
            newCustomer.setEmail(cartDTO.getCustomerDTO().getEmail());
            newCustomer.setPhone(cartDTO.getCustomerDTO().getPhone());
            newCustomer.setBirthDate(cartDTO.getCustomerDTO().getBirthDate());
            newCustomer.setGender(cartDTO.getCustomerDTO().getGender());

            return this.customerRepository.save(newCustomer);

        });

//        Optional<Customer> optionalCustomer = this.customerRepository.findById(cartDTO.getCustomerId());
//        Customer customer = optionalCustomer.get();

//        if (customer == null) {
//            Customer newCustomer = new Customer();
//            customer.setName(customerDTO.getName());
//            customer.setEmail(customerDTO.getEmail());
//            customer.setPhone(customerDTO.getPhone());
//            customer.setBirthDate(customerDTO.getBirthDate());
//            customer.setGender(customerDTO.getGender());
//
//            this.customerRepository.save(customer);
//        }


        cart.setTotalPrice(cartDTO.getTotalPrice());
        cart.setCustomer(customer);

        Cart savedCart = this.cartRepository.save(cart);
        return savedCart;
    }

    @Override
    @Transactional
    public Cart updateCart(Long id, CartDTO cartDTO) {
        Cart cart = this.cartRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Related cart cannot be found with relevant id" + id));


        Customer customer = this.customerRepository.findById(cartDTO.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with id: " + cartDTO.getCustomerId()));

        cart.setTotalPrice(cartDTO.getTotalPrice());

        cart.setCustomer(customer);

        Cart cartToBeUpdated = this.cartRepository.save(cart);
        return cartToBeUpdated;

    }

    @Override
    @Transactional
    public void deleteCart(Long id) {
        Cart cart = this.cartRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found with id:" + id));

        this.cartRepository.delete(cart);

    }


    @Override
    public Cart addToCart(Long cartId, CartItem cartItem) {
        Cart cart = this.getCartById(cartId);
        cart.getItems().add(cartItem); // set the cart side.
        cartItem.setCart(cart); // set the cartItem side.
        updateTotalPrice(cart); // stay up-to-date the total price of the cart.
        return cartRepository.save(cart); // save the cart to the DB.
    }

    @Override
    public Cart removeFromCart(Long cartId, Long cartItemId) {
        Cart cart = this.getCartById(cartId);

        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        //lambda expression to remove items from a list based on a specified condition
        //item -> item.getId().equals(cartItemId) is a lambda expression  and it means
        // "for each item in the list, remove it if item.getId() equals cartItemId
        // which is coming from FE side (for example).

        updateTotalPrice(cart);
        return cartRepository.save(cart);
    }

    @Override
    public Cart increaseQuantity(Long cartId, Long cartItemId, int quantity) {
        Cart cart = this.getCartById(cartId);
        cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .forEach(item -> item.setQuantity(item.getQuantity() + quantity));

        //.filter(item -> item.getId().equals(cartItemId)):
        //The filter method is an intermediate operation in the Stream API that returns a new Stream consisting of the elements that match the given predicate.
        //item -> item.getId().equals(cartItemId) is a lambda expression serving as the predicate. It means "include only those items in the stream where item.getId() equals cartItemId."
        //.forEach(item -> item.setQuantity(item.getQuantity() + quantity)):
        //The forEach method is a terminal operation that performs an action for each element of the Stream.
        //item -> item.setQuantity(item.getQuantity() + quantity) is a lambda expression defining the action to be performed.
        // It means "for each item in the filtered stream, update its quantity by adding the specified quantity to its current quantity."

        updateTotalPrice(cart);
        return cartRepository.save(cart);
    }

    @Override
    public Cart decreaseQuantity(Long cartId, Long cartItemId, int quantity) {
        Cart cart = this.getCartById(cartId);

        cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .forEach(item -> item.setQuantity(item.getQuantity() - quantity));

        updateTotalPrice(cart);
        return cartRepository.save(cart);
    }


    // calculates the totalPrice of the related Cart.
    @Override
    public double calculateTotalPrice(Cart cart) {
        return cart.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    //stay up-to-date the totalPrice of the Cart.
    private void updateTotalPrice(Cart cart) {
        cart.setTotalPrice(calculateTotalPrice(cart));
    }


}
