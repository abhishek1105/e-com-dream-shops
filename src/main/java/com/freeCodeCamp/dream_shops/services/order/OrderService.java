package com.freeCodeCamp.dream_shops.services.order;

import com.freeCodeCamp.dream_shops.dto.OrderDto;
import com.freeCodeCamp.dream_shops.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
