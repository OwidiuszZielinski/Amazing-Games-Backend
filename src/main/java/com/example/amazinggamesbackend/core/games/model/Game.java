package com.example.amazinggamesbackend.core.games.model;

import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.orders.model.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    //Powinno sie uzywac id np gameId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String type;
    private double price;
    @Column(length = 500)
    private String description;
    private double rating;
    private boolean availability;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "order_game", joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartDetail> cartDetails = new ArrayList<>();

    public void fromDTO(GameDTO gameDTO) {
        this.title = gameDTO.getTitle();
        this.type = gameDTO.getType();
        this.price = gameDTO.getPrice();
        this.description = gameDTO.getDescription();
        this.rating = gameDTO.getRating();
        this.availability = gameDTO.isAvailability();
    }


}
