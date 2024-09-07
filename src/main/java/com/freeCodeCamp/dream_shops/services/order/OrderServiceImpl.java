package com.freeCodeCamp.dream_shops.services.order;

import com.freeCodeCamp.dream_shops.exceptions.ResourceNotFoundException;
import com.freeCodeCamp.dream_shops.model.Order;
import com.freeCodeCamp.dream_shops.model.OrderItem;
import com.freeCodeCamp.dream_shops.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;


    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Cant find any Order"));
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
