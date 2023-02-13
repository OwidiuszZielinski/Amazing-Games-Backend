package com.example.amazinggamesbackend.app;


import com.example.amazinggamesbackend.core.baseclasses.DeleteNotification;
import com.example.amazinggamesbackend.core.baseclasses.Notification;
import com.example.amazinggamesbackend.core.users.UserRepository;
import com.example.amazinggamesbackend.core.users.UserService;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/")
public class UserController {
    private final UserService userService;

    @ExceptionHandler({ IllegalArgumentException.class, NotFoundException.class })
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(summary = "Get users")
    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int userId) {
        UserDTO user = userService.getUserMapToDTO(userId);
        return new ResponseEntity<>(user ,HttpStatus.OK);

    }

    @Operation(summary = "Delete users")
    @DeleteMapping
    public ResponseEntity<Notification> deleteUsers(@RequestBody List<Integer> ids) {
        userService.deleteUsers(ids);
        return new ResponseEntity<>(new DeleteNotification("DELETED"),HttpStatus.OK);
    }

    @Operation(summary = "Update user by ID")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id , @Valid @RequestBody UserDTO user) {
        UserDTO edited = userService.updateUser(id, user);
        return new ResponseEntity<>(edited,HttpStatus.ACCEPTED);

    }

}

