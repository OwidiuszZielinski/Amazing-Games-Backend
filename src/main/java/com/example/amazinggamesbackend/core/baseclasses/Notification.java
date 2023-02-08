package com.example.amazinggamesbackend.core.baseclasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Notification {
    protected String message;
    protected abstract String getMessage(String msg);
}
