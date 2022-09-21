package com.example.amazinggamesbackend.core.games.model;

import com.example.amazinggamesbackend.core.shoppingcart.model.CartDetail;
import com.example.amazinggamesbackend.core.shoppingcart.model.ShoppingCartEntity;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class GameEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String type;
    private double price;
    @Column(length = 500)
    private String description;
    private double rating;
    private boolean availability;


    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST ,CascadeType.MERGE ,CascadeType.DETACH ,CascadeType.REFRESH })
    @JoinTable(name = "order_game", joinColumns = @JoinColumn(name = "game_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
    @JsonBackReference
    private List<OrderEntity> orders = new ArrayList<>();

    //@JsonIgnore
    @OneToMany(mappedBy = "game",fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST ,CascadeType.MERGE ,CascadeType.DETACH ,CascadeType.REFRESH })
    private List<CartDetail> cartDetails = new ArrayList<>();

    public void setRating(double rating) {
        if (rating <= 10 && rating >= 0) {
            this.rating = rating;
        } else throw new IllegalArgumentException("bad rating value");
    }

    public void fromDTO(GameDTO gameDTO) {
        this.title = gameDTO.getTitle();
        this.type = gameDTO.getType();
        this.price = gameDTO.getPrice();
        this.description = gameDTO.getDescription();
        this.rating = gameDTO.getRating();
        this.availability = gameDTO.isAvailability();
    }

}
