package com.example.amazinggamesbackend.core.games.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class GameDayDiscount {

    @Id
    @GeneratedValue
    @Column(name = "discount_id")
    private int id;

    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;


}
