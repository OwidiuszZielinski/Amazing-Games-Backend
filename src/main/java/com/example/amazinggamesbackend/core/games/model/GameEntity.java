package com.example.amazinggamesbackend.core.games.model;

import com.example.amazinggamesbackend.core.cart.model.CartDetail;
import com.example.amazinggamesbackend.core.games.dto.GameEntityDTO;
import com.example.amazinggamesbackend.core.orders.model.OrderEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private List<OrderEntity> orders = new ArrayList<>();
    @OneToMany(mappedBy = "game", orphanRemoval = true,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CartDetail> cartDetails = new ArrayList<>();

    public void setRating(double rating) {
        if (rating <= 10 && rating >= 0) {
            this.rating = rating;
        } else throw new IllegalArgumentException("Bad rating value");
    }

    public void fromDTO(GameEntityDTO gameDTO) {
        this.title = gameDTO.getTitle();
        this.type = gameDTO.getType();
        this.price = gameDTO.getPrice();
        this.description = gameDTO.getDescription();
        this.rating = gameDTO.getRating();
        this.availability = gameDTO.isAvailability();
    }

}
