package com.freeCodeCamp.dream_shops.repo;

import com.freeCodeCamp.dream_shops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
