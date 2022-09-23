package com.example.amazinggamesbackend.core.cart.model;

import com.example.amazinggamesbackend.core.cart.dto.CartDTO;
import com.example.amazinggamesbackend.core.cart.dto.CartDetailDTO;
import com.example.amazinggamesbackend.core.cart.dto.GameEntityDTO;
import com.example.amazinggamesbackend.core.games.dto.GameDTO;
import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class CartEntity {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartDetail> cartDetails = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")

    private UserEntity user;


    public void addUser(UserEntity userEntity) {
        this.user = userEntity;
    }

    public void fromDTO(CartDTO cartDTO) {
        List<CartDetail> templist = new ArrayList<>();
        for (CartDetailDTO x : cartDTO.getCartDetailsinfo()) {
            templist.add(new CartDetail(toGameEntity(x.getGame()) ,x.getQuantity()));
        }
        this.cartDetails = templist;
        this.user = cartDTO.getUser();

    }


    public static GameEntity toGameEntity(GameEntityDTO gameEntityDTO) {
        GameEntity game = new GameEntity();
        game.setId(gameEntityDTO.getId());
        game.setPrice(gameEntityDTO.getPrice());
        game.setRating(gameEntityDTO.getRating());
        game.setTitle(gameEntityDTO.getTitle());
        game.setAvailability(gameEntityDTO.isAvailability());
        game.setDescription(gameEntityDTO.getDescription());
        game.setType(gameEntityDTO.getType());
        return game;

    }



}
