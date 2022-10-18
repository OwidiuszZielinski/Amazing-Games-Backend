package com.example.amazinggamesbackend.core.users.model;

import lombok.*;

@Getter // Defines the getter methods of the member fields
@Setter // Defines the setter methods of the member fields
@AllArgsConstructor // Defines a constructor that initializes all the member fields
@NoArgsConstructor // Defines no-args default constructor
@ToString // Defines a meaningful toString implementation of this class
public class LoginCredentials {

    private String username;
    private String password;

}
