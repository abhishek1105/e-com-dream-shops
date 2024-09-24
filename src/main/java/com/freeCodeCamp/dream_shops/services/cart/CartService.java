package com.freeCodeCamp.dream_shops.services.cart;

import com.freeCodeCamp.dream_shops.model.Cart;
import com.freeCodeCamp.dream_shops.model.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);



    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
