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
    private String groupe;
    private double price;
    private String description;
    private double rating;
    private boolean availability;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }




    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }


    public void fromDTO(GamesDTO gamesDTO){
        this.title = gamesDTO.getTitle();
        this.groupe = gamesDTO.getGroupe();
        this.price = gamesDTO.getPrice();
        this.description = gamesDTO.getDescription();
        this.rating = gamesDTO.getRating();
        this.availability = gamesDTO.isAvailability();
    }

}
