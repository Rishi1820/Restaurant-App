package com.restaurant.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.restaurant.entity.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

}
