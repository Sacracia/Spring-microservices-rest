package com.example.ticketservice.repository;

import com.example.ticketservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    List<Order> findAllByStatus(Integer status);
}
