package com.example.amazinggamesbackend.core.shoppingcart;


import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ShoppingCartGamesRelation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer quantity;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY ,cascade = {CascadeType.ALL})
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "game_id")
    private GameEntity game;


}
