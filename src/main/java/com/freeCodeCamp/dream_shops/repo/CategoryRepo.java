package com.freeCodeCamp.dream_shops.repo;

import com.freeCodeCamp.dream_shops.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {

    Category findByName(String name);

    boolean existsByName(String name);
}
