package com.example.amazinggamesbackend.core.cart.model;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartDetail {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    //@JsonIgnore
    private CartEntity cart;

    private int quantity;

    public CartDetail(GameEntity game,int quantity) {
        this.game = game;
        this.quantity = quantity;
    }

    public CartDetail(GameEntity game,CartEntity cart, int quantity) {
        this.game = game;
        this.cart = cart;
        this.quantity = quantity;
    }

    public void increaseQty(){
        this.quantity = quantity+1;
    }


}
