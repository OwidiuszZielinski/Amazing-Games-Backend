package com.example.amazinggamesbackend.core.games.model;

import com.example.amazinggamesbackend.core.games.dto.GamesDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class GamesEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String type;
    private double price;
    private String description;
    private double rating;
    private boolean availability;


    @ManyToMany( fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinTable(name = "order_game",
    joinColumns = @JoinColumn (name = "game_id"),
    inverseJoinColumns = @JoinColumn(name = "order_id"))
    @JsonBackReference
    private List<OrderEntity> orders = new ArrayList<>();
    public void setRating(double rating) {
        if (rating <= 10 && rating >=0) {
            this.rating = rating;
        }
        else
            throw new IllegalArgumentException("bad rating value");
    }

    public void fromDTO(GamesDTO gamesDTO){
        this.title = gamesDTO.getTitle();
        this.type = gamesDTO.getType();
        this.price = gamesDTO.getPrice();
        this.description = gamesDTO.getDescription();
        this.rating = gamesDTO.getRating();
        this.availability = gamesDTO.isAvailability();
    }

}
