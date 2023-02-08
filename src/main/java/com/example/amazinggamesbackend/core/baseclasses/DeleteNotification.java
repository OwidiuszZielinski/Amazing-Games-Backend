package com.example.amazinggamesbackend.core.baseclasses;

public class DeleteNotification extends Notification{
    public DeleteNotification(String message) {
        super(message);
    }

    @Override
    protected String getMessage(String msg) {
        return msg;
    }
}
