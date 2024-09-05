package com.freeCodeCamp.dream_shops.services.cart;

import com.freeCodeCamp.dream_shops.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();
}
