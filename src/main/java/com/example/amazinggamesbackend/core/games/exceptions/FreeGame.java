package com.example.amazinggamesbackend.core.games.exceptions;

public class FreeGame extends RuntimeException{
    public FreeGame() {
        super("No paid game in DB");
    }
}
