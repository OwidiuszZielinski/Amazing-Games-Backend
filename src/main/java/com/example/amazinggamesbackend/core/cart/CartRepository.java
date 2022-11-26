package com.example.amazinggamesbackend.core.cart;

import com.example.amazinggamesbackend.core.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
     Optional<Cart> findByUserId(Integer id);



}
