package com.ecommerdedemo.ecommercedemo.business.concretes;

import com.ecommerdedemo.ecommercedemo.business.abstracts.CartService;
import com.ecommerdedemo.ecommercedemo.business.abstracts.CustomerService;
import com.ecommerdedemo.ecommercedemo.business.abstracts.HistoricalPriceService;
import com.ecommerdedemo.ecommercedemo.business.abstracts.OrderService;
import com.ecommerdedemo.ecommercedemo.business.dtos.OrderDTO;
import com.ecommerdedemo.ecommercedemo.business.exceptions.InsufficientStockException;
import com.ecommerdedemo.ecommercedemo.business.exceptions.ResourceNotFoundException;
import com.ecommerdedemo.ecommercedemo.entities.concretes.*;
import com.ecommerdedemo.ecommercedemo.repository.abstracts.OrderRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderManager implements OrderService {

    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final CustomerService customerService;
    private final HistoricalPriceService historicalPriceService;

    public OrderManager(OrderRepository orderRepository, CartService cartService, CustomerService customerService, HistoricalPriceService historicalPriceService) { // DI
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.customerService = customerService;
        this.historicalPriceService = historicalPriceService;
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        Order order = this.orderRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("The related Order cannot be found with relevant id" + id));

        return order;
    }

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();

        order.setOrderDate(orderDTO.getOrderDate());
        order.setCompleted(orderDTO.isCompleted());
        order.setPaymentMethod(orderDTO.getPaymentMethod());

        Order savedOrder = this.orderRepository.save(order);
        return savedOrder;

    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        Order order = this.orderRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("The related Order cannot be found with relevant id" + id));
        order.setOrderDate(orderDTO.getOrderDate());
        order.setCompleted(orderDTO.isCompleted());
        order.setPaymentMethod(orderDTO.getPaymentMethod());

        Order orderToBeUpdated = this.orderRepository.save(order);
        return orderToBeUpdated;

    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = this.orderRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("The related Order cannot be found with relevant id" + id));

        this.orderRepository.delete(order);
    }


    // Placing order -> which processes an order for a customer based on the contents of their cart.

    // Summary of This method:
    //Retrieves the cart and customer using their respective service methods.
    //Creates a new order and sets its details (date, total, customer).
    //Converts the items in the cart to order items and adds them to the order.
    //Saves the order to the database and returns the saved order.
//    @Override
//    public Order placeOrder(Long cartId, Long customerId) {
//        Cart cart = this.cartService.getCartById(cartId);
//        Customer customer = this.customerService.getCustomerById(customerId);
//
//        Order order = new Order(); // constitute a new Order
//
//        // setting the attributes of the related order
//        order.setOrderDate(LocalDate.now());
//        order.setTotal(cart.getTotalPrice()); // we use  cart pointer in this statement
//        order.setCustomer(customer); // we use customer pointer in this statement
//
//        //This block converts each CartItem in the cart to an OrderItem for the order.
//
//        //Streams and Mapping:
//
//        //cart.getItems().stream() creates a Stream from the list of cart items.
//        //.map(cartItem -> { ... }) processes each cart item.
//        //For each cartItem, a new OrderItem is created.
//        //The OrderItem's properties (product, quantity, price, order) are set based on the cartItem.
//        //The OrderItem is added to the list of order items.
//        //The collect(Collectors.toList()) collects all the OrderItem objects into a list and sets this list to the order's items.
//        order.setItems(cart.getItems().stream().map(cartItem -> {
//            OrderItem orderItem = new OrderItem();
//
//            orderItem.setProduct(cartItem.getProduct());
//            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setPrice(cartItem.getPrice());
//
//            orderItem.setOrder(order); //set the Order of the orderItem.
//
//            return orderItem;
//
//        }).collect(Collectors.toList()));
//
//        this.saveOrderWithHistoricalPrices(order);

//        //return this.orderRepository.save(order);
//        // #Important: Instead of saving the order in this place,
//        // first we instantiate the HistoricalPrice entity and
//        // set the  product, price and purchaseDateTime attributes  of it and
//        // finally we set the historicalPrice attribute of the orderItem object.
//        // And as we know that List of OrderItem is an attribute of -> the Order object, so we also
//        //set the related Order too as necessary.
//
//        return order;
//
//
//    }


    @Override
    public Order placeOrder(Long cartId, Long customerId) {

        Cart cart = this.cartService.getCartById(cartId);
        Customer customer = this.customerService.getCustomerById(customerId);

        Order order = new Order();

        order.setOrderDate(LocalDate.now());
        order.setTotal(cart.getTotalPrice()); // we use cart pointer in this statement
        order.setCustomer(customer); // we use customer pointer in this statement


        List<OrderItem> orderItems = new ArrayList<>();

        // If cart is not empty, convert cart items to -> order items.
        if (!cart.getItems().isEmpty()) {

            // Check if all products in the cart have sufficient stock
            for (CartItem cartItem : cart.getItems()) {
                if (cartItem.getProduct().getStock() < cartItem.getQuantity()) {
                    throw new InsufficientStockException("Insufficient stock for product: " + cartItem.getProduct().getName());
                }
            }



            //The warning message "Variable used in lambda expression should be final or effectively final" is issued
            // because we're accessing the order variable inside the lambda expression created by map.
            // Lambda expressions can only access local variables that are effectively final,
            // meaning their value doesn't change after they are initialized.
            // In our case, you're modifying the order variable inside the lambda expression
            // by calling order.setItems(orderItems), which makes it not effectively final.
            // To resolve this issue,
            // we  can create a final copy of the order variable that can be safely accessed inside the lambda expression.
            // Here's how we  can refactor our  code to address this warning:
            //By creating a final copy of the order variable (finalOrder),
            // we can safely access it inside the lambda expression without causing the compiler warning.
            final Order finalOrder = order; // Create a final copy of the order variable
            orderItems.addAll(cart.getItems().stream().map(cartItem -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getProduct().getCurrentPrice());
                orderItem.setOrder(finalOrder); // we use order (namely finalOrder) pointer in this statement and set the Order of the related orderItem.
                return orderItem;
            }).collect(Collectors.toList()));
        }

        // we set the orderItems attribute,
        // namely set the List of OrderItem attribute of the related Order.
        order.setItems(orderItems);


        //save the order to the database
        order = this.orderRepository.save(order);


        // we call the method that Save historical prices for the order items
        this.saveOrderWithHistoricalPrices(order);


        return order;

    }


    private void saveOrderWithHistoricalPrices(Order order) {
        for (OrderItem orderItem : order.getItems()) {
            this.historicalPriceService.saveHistoricalPrice(orderItem.getProduct(), orderItem.getProduct().getCurrentPrice());
        }
    }

}
