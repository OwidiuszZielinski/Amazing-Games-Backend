package com.example.amazinggamesbackend.core.games;

import com.example.amazinggamesbackend.core.games.model.GamesEntity;
import com.example.amazinggamesbackend.core.users.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamesRepository extends JpaRepository<GamesEntity,Integer> {
    List<GamesEntity> findByGameByTitle(String gameTitle);

}
