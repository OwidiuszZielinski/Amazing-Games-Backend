package com.example.amazinggamesbackend.core.games.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class GameDayDiscount {

    @Id
    @GeneratedValue
    @Column(name = "discount_id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;


}
