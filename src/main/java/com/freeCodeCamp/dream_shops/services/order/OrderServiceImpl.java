package com.freeCodeCamp.dream_shops.services.order;

import com.freeCodeCamp.dream_shops.enums.OrderStatus;
import com.freeCodeCamp.dream_shops.exceptions.ResourceNotFoundException;
import com.freeCodeCamp.dream_shops.model.Cart;
import com.freeCodeCamp.dream_shops.model.Order;
import com.freeCodeCamp.dream_shops.model.OrderItem;
import com.freeCodeCamp.dream_shops.model.Product;
import com.freeCodeCamp.dream_shops.repo.OrderRepo;
import com.freeCodeCamp.dream_shops.repo.ProductRepo;
import com.freeCodeCamp.dream_shops.services.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final CartService cartService;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setOrderTotalAmount(cart.getTotalAmount());
        Order savedOrder = orderRepo.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Cant find any Order"));
    }

    @Override
    public List<Order> getUserOrders(Long userId) {

        return orderRepo.findByUserId(userId);

    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepo.save(product);
            return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getUnitPrice());
        }).toList();
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream().map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
