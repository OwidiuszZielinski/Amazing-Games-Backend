package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final  PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder ,UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserDTO getUser(int id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.from(user);
    }

    public List<UserDTO> getAllUsers(){
        List<UserDTO> tempList = new ArrayList<>();
        for(User x : userRepository.findAll()){
            tempList.add(UserDTO.fromWithoutPassword(x));

        }
        return tempList;
    }
    public void deleteUsers(List<Integer> ids){
        userRepository.deleteAllByIdInBatch(ids);
    }
    public void updateUser(int id,UserDTO user){
        User update = userById(id);
        update.fromDTO(user);
        if(user.getPassword().isBlank()) {
            update.setPassword(update.getPassword());
        }
        else {
            update.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(update);
    }
    public User userById(int id) {
            return userRepository.findById(id).get();
        }

}
