package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.UsersService;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UsersController {



    private final UsersRepository usersRepository;

    private final UsersService usersService;
    @Autowired
    public UsersController(UsersRepository usersRepository ,UsersService usersService) {
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    @Operation(summary = "Get users")
    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return usersService.getAllUsers();
    }

    @Operation(summary = "Delete users")
    @DeleteMapping("/users")
    public ResponseEntity<Object> deleteUsers(@RequestBody List<Integer> ids) {
        if (usersRepository.findAllById(ids).isEmpty() || ids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

        }
        usersService.deleteUsers(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update user by ID")
    @PatchMapping("/users/{id}")
    public void updateUser(@PathVariable int id ,@RequestBody UserDTO user) {
        usersService.updateUser(id ,user);
    }

}

