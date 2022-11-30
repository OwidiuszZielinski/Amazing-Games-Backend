package com.example.amazinggamesbackend.core.games.exceptions;

public class GameNotFound extends RuntimeException{
    public GameNotFound(String message) {
        super(message);
    }
}
