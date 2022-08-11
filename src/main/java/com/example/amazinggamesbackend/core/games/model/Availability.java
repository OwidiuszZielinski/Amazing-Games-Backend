package com.example.amazinggamesbackend.core.games.model;

public interface Availability {
    default boolean setGameAvailability(boolean availability){
        return !availability;
    }
}
