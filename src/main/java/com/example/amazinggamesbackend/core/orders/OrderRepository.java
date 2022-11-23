package com.example.amazinggamesbackend.core.orders;


import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {


}
