package com.freeCodeCamp.dream_shops.repo;

import com.freeCodeCamp.dream_shops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
}
