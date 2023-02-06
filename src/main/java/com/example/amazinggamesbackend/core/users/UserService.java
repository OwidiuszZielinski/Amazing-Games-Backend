package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final  PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder ,UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserDTO getUserMapToDTO(int id){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return UserDTO.from(user);
    }

    public List<UserDTO> getAllUsers(){
        List<UserDTO> tempList = new ArrayList<>();
        for(User x : Users()){
            tempList.add(UserDTO.fromWithoutPassword(x));

        }
        return tempList;
    }

    private List<User> Users() {
        List<User> userRepositoryAll = userRepository.findAll();
        if(userRepositoryAll.isEmpty()){
            throw new NotFoundException("No users in DB");
        }
        return userRepositoryAll;
    }

    public void deleteUsers(List<Integer> ids){
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

    public UserDTO updateUser(int id,UserDTO user){
        User update = userById(id);
        if(validateEmail(user)||validateUsername(user)){
            throw new IllegalArgumentException("email or username exists or to short");
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


    public boolean validateUsername(UserDTO user){
        final String userName = user.getUsername();
        if(userName.length()<6){
            return true;
        }
       return getAllUsers().stream().anyMatch(u->u.getUsername().equals(userName));


    }

    public boolean validateEmail(UserDTO user){

        final String userEmail = user.getEmail();
        if(userEmail.length()<6){
            return true;
        }
        return getAllUsers().stream().anyMatch(u->u.getUsername().equals(userEmail));

    }


    public User userById(int id) {
            return userRepository.findById(id).orElseThrow(()-> new NoSuchElementException("User not found"));
        }


}
