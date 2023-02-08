package com.example.amazinggamesbackend.core.cart.exceptions;

public class CartNotFound extends RuntimeException {
    public CartNotFound(String message) {
        super(message);
    }
}
