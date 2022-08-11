package com.example.amazinggamesbackend.core.games.model;

import com.example.amazinggamesbackend.core.games.dto.GamesDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GamesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String group;
    private double price;
    private String description;
    private double rating;
    private boolean availability;


    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }


    public void fromDTO(GamesDTO gamesDTO){
        this.title = gamesDTO.getTitle();
        this.group = gamesDTO.getGroup();
        this.price = gamesDTO.getPrice();
        this.description = gamesDTO.getDescription();
        this.rating = gamesDTO.getRating();
        this.availability = gamesDTO.isAvailability();
    }

}
