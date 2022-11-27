package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.users.UserRepository;
import com.example.amazinggamesbackend.core.users.UserService;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;


@RestController
public class UserController {


    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository ,UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Operation(summary = "Get users")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        try {
            List<UserDTO> users = userService.getAllUsers();
            return new ResponseEntity<>(users ,HttpStatus.OK);
        }
        catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Operation(summary = "Get user")
    @GetMapping("/users/{Id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int Id) {
        try {
            UserDTO user = userService.getUserMapToDTO(Id);
            return new ResponseEntity<>(user ,HttpStatus.OK);
        }
        catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Operation(summary = "Delete users")
    @DeleteMapping("/users")
    public ResponseEntity<Integer> deleteUsers(@RequestBody List<Integer> ids) {
        try {
            userService.deleteUsers(ids);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @Operation(summary = "Update user by ID")
    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id ,@RequestBody UserDTO user) {
        try {
            userService.updateUser(id,user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

}

