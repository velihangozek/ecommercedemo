package com.ecommerdedemo.ecommercedemo.controllers;


import com.ecommerdedemo.ecommercedemo.business.abstracts.HistoricalPriceService;
import com.ecommerdedemo.ecommercedemo.business.abstracts.OrderService;
import com.ecommerdedemo.ecommercedemo.business.dtos.OrderDTO;
import com.ecommerdedemo.ecommercedemo.entities.concretes.HistoricalPrice;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private OrderService orderService;
    private final HistoricalPriceService historicalPriceService;

    public OrdersController(OrderService orderService, HistoricalPriceService historicalPriceService) { //DI
        this.orderService = orderService;
        this.historicalPriceService = historicalPriceService;
    }

    // GET all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // GET an order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // POST a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        Order createdOrder = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // PUT update an order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        Order updatedOrder = orderService.updateOrder(id, orderDTO);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    // DELETE an order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //placeOrder end-point
    @PostMapping("/place/{cartId}/{customerId}")
    public ResponseEntity<Order> placeOrder(@PathVariable Long cartId, @PathVariable Long customerId) {
        Order order = this.orderService.placeOrder(cartId, customerId);
        return ResponseEntity.ok(order);
    }



    // getHistoricalPricesForRelatedOrder end-point
    @GetMapping("/{orderId}/historical-prices")
    public ResponseEntity<List<HistoricalPrice>> getHistoricalPricesForRelatedOrder(@PathVariable Long orderId) {

        Order order = this.orderService.getOrderById(orderId);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        List<HistoricalPrice> historicalPrices = this.historicalPriceService.getHistoricalPricesForRelatedOrder(order);
        return ResponseEntity.ok(historicalPrices);
    }


}
