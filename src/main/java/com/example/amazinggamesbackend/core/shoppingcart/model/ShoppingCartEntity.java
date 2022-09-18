package com.example.amazinggamesbackend.core.shoppingcart.model;

import com.example.amazinggamesbackend.core.games.model.GameEntity;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class ShoppingCartEntity  {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany(mappedBy = "cart",fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST ,CascadeType.MERGE ,CascadeType.DETACH ,CascadeType.REFRESH })
    @JsonManagedReference
    private List<CartDetail> cartDetails = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public void addUser(UserEntity userEntity) {
        this.user = userEntity;
    }



}
