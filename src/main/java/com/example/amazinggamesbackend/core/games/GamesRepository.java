package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GamesRepository extends JpaRepository<GameEntity, Integer> {


}
