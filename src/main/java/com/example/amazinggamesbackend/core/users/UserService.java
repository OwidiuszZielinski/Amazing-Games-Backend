package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    public UserDTO getUserMapToDTO(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return UserDTO.fromWithoutPassword(user);
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> tempList = new ArrayList<>();
        for (User user : Users()) {
            tempList.add(UserDTO.fromWithoutPassword(user));
        }
        return tempList;
    }

    private List<User> Users() {
        List<User> userRepositoryAll = userRepository.findAll();
        if (userRepositoryAll.isEmpty()) {
            throw new NotFoundException("No users in DB");
        }
        return userRepositoryAll;
    }

    public void deleteUsers(List<Integer> userIds) {
        if (userIds.isEmpty()) {
            throw new IllegalArgumentException("Ids is empty");
        }
        if (getAllById(userIds).isEmpty()) {
            throw new IllegalArgumentException("Users not found in DB");
        }
        userRepository.deleteAllByIdInBatch(userIds);
    }

    private List<User> getAllById(List<Integer> userIds) {
        return userRepository.findAllById(userIds);
    }

    public UserDTO updateUser(int userId, @Valid UserDTO user) throws IllegalArgumentException {
        User update = userById(userId);
        if (checkUserUsernameExists(user, update) || checkUserEmailExists(user, update)) {
            throw new IllegalArgumentException("email or username exists");
        }
        update.fromDTO(user);
        if (user.getPassword().isBlank()) {
            update.setPassword(update.getPassword());
        } else {
            update.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(update);
        return user;
    }


    public boolean validateUsername(UserDTO user) {
        return user.getEmail().length() < 6;

    }

    public boolean checkUserUsernameExists(UserDTO user, User updateUser) {
        boolean match = getAllUsers().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));
        boolean checkTheSame = user.getUsername().equals(updateUser.getUsername());
        return match && !checkTheSame;
    }

    public boolean validateEmail(UserDTO user) {
        return user.getEmail().length() < 6;


    }

    public boolean checkUserEmailExists(UserDTO user, User updateUser) {
        boolean match = getAllUsers().stream().anyMatch(u -> u.getUsername().equals(user.getEmail()));
        boolean checkTheSame = user.getEmail().equals(updateUser.getEmail());
        return match && !checkTheSame;
    }


    public User userById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

}
