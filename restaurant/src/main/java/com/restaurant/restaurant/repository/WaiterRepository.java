package com.restaurant.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.restaurant.entity.Waiter;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Long> {

}
