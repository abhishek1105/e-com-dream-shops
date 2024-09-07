package com.freeCodeCamp.dream_shops.services.order;

import com.freeCodeCamp.dream_shops.model.Order;

public interface OrderService {
    Order placeOrder(Long userId);

    Order getOrder(Long orderId);
}
