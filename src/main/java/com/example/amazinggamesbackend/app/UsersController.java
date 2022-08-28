package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.UsersService;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UsersController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;

    @Operation(summary = "add user")
    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody UserDTO userDTO) {

        if (usersRepository.findByUsernameIgnoreCase(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else if (userDTO.getUsername().isBlank() || userDTO.getPassword().isBlank() || userDTO.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            usersService.addUser(userDTO);
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "get users")
    @GetMapping("/users")
    public List<UserEntity> getUsers() {
        return usersService.getAllUsers();
    }

    @Operation(summary = "delete user by id")
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        usersService.deleteUser(id);
    }

    @Operation(summary = "edit user by id")
    @PatchMapping("/users/{id}")
    public UserEntity editUser(@PathVariable int id ,@RequestBody UserDTO user) {
        return usersService.editUser(id ,user);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDTO user) {
        Optional<UserEntity> userFromDb = usersRepository.findByUsernameIgnoreCase(user.getUsername());
        return ResponseEntity.ok().build();
    }
}

