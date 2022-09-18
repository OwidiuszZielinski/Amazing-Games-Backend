package com.example.amazinggamesbackend.core.shoppingcart;

import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Integer> {
     Optional<ShoppingCartEntity> findByUserId(int id);


}
