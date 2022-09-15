package com.example.amazinggamesbackend.core.shoppingcart;


import com.example.amazinggamesbackend.core.games.model.GameEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cart_game")
public class ShoppingCartGames implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;



    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "game_id")
    private GameEntity game;



}
