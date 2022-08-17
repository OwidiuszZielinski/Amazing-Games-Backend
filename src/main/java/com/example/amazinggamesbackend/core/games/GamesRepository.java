package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<GamesEntity,Integer> {

}
