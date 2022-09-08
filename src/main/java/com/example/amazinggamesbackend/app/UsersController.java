package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.dto.DeleteUsersDTO;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.UsersService;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UsersController {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;

    @Operation(summary = "get users")
    @GetMapping("/users")
    public List<UserEntity> getUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/users/basket")
    public UserEntity getUserDetails() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usersRepository.findByUsernameIgnoreCase(username).get();
    }

    @Operation(summary = "delete users")
    @DeleteMapping("/users")
    public ResponseEntity<Object> deleteUsers(@RequestBody DeleteUsersDTO deleteUsersDTO) {
        if (deleteUsersDTO.getIds().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        if (usersRepository.findAllById(deleteUsersDTO.getIds()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        usersService.deleteUsers(deleteUsersDTO.getIds());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "edit user by id")
    @PatchMapping("/users/{id}")
    public UserEntity editUser(@PathVariable int id ,@RequestBody UserDTO user) {
        return usersService.editUser(id ,user);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDTO user) {
        Optional<UserEntity> userFromDb = usersRepository.findByUsernameIgnoreCase(user.getUsername());
        return ResponseEntity.ok().build();
    }
}

