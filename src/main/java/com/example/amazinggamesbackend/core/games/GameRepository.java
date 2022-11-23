package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GameRepository extends JpaRepository<GameEntity, Integer> {
    Optional<GameEntity> findById(Integer id);

}
