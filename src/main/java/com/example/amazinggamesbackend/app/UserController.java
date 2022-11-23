package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.users.UserRepository;
import com.example.amazinggamesbackend.core.users.UserService;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get users")
    @GetMapping("/users/{Id}")
    public UserDTO getUsers(@PathVariable int Id) {
        return userService.getUser(Id);
    }

    @Operation(summary = "Delete users")
    @DeleteMapping("/users")
    public ResponseEntity<Object> deleteUsers(@RequestBody List<Integer> ids) {
        if (userRepository.findAllById(ids).isEmpty() || ids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        }
        userService.deleteUsers(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update user by ID")
    @PatchMapping("/users/{id}")
    public void updateUser(@PathVariable int id ,@RequestBody UserDTO user) {
        userService.updateUser(id ,user);
    }

}

