package com.freeCodeCamp.dream_shops.repo;

import com.freeCodeCamp.dream_shops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
