package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserDTO getUserMapToDTO(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return UserDTO.from(user);
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> tempList = new ArrayList<>();
        for (User x : Users()) {
            tempList.add(UserDTO.fromWithoutPassword(x));

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

    public void deleteUsers(List<Integer> ids) {
        if (ids.isEmpty()) {
            throw new IllegalArgumentException("Ids is empty");
        }
        if (getAllById(ids).isEmpty()) {
            throw new IllegalArgumentException("Users not found in DB");
        }
        userRepository.deleteAllByIdInBatch(ids);
    }

    private List<User> getAllById(List<Integer> ids) {
        return userRepository.findAllById(ids);
    }

    public UserDTO updateUser(int id, @Valid UserDTO user) throws IllegalArgumentException{
        User update = userById(id);
        if(checkUserUsernameExists(user,update) || checkUserEmailExists(user,update)){
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

    public boolean checkUserUsernameExists(UserDTO user,User updateUser){
        boolean match = getAllUsers().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));
        boolean checkTheSame = user.getUsername().equals(updateUser.getUsername());
        return match && !checkTheSame;
    }

    public boolean validateEmail(UserDTO user) {
        return user.getEmail().length() < 6;


    }
    public boolean checkUserEmailExists(UserDTO user,User updateUser){
        boolean match = getAllUsers().stream().anyMatch(u -> u.getUsername().equals(user.getEmail()));
        boolean checkTheSame = user.getEmail().equals(updateUser.getEmail());
        return match && !checkTheSame;
    }


    public User userById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }


}
