package com.freeCodeCamp.dream_shops.services.order;

import com.freeCodeCamp.dream_shops.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);

    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
