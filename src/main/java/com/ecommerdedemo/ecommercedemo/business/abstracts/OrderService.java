package com.ecommerdedemo.ecommercedemo.business.abstracts;

import com.ecommerdedemo.ecommercedemo.business.dtos.OrderDTO;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Order;

import java.util.List;

public interface OrderService {
    public List<Order> getAllOrders();

    public Order getOrderById(Long id);

    public Order createOrder(OrderDTO orderDTO);

    public Order updateOrder(Long id, OrderDTO orderDTO);

    public void deleteOrder(Long id);

    Order placeOrder(Long cartId, Long customerId);
}
