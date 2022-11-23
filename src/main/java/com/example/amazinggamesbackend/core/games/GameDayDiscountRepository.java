package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.model.GameDayDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameDayDiscountRepository extends JpaRepository<GameDayDiscount, Integer> {
}
