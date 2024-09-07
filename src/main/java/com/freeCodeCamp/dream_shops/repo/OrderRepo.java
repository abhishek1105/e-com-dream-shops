package com.freeCodeCamp.dream_shops.repo;

import com.freeCodeCamp.dream_shops.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
