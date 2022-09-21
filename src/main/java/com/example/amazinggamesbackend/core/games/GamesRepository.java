package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.shoppingcart.dto.AddToCartDTO;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GamesRepository extends JpaRepository<GameEntity, Integer> {
    Optional<GameEntity> findById(Integer id);

}
