package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findById(Integer id);

}
