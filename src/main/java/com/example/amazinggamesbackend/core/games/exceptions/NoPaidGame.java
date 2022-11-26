package com.example.amazinggamesbackend.core.games.exceptions;

public class NoPaidGame extends RuntimeException{
    public NoPaidGame() {

        super("No paid game in DB");
    }
}
