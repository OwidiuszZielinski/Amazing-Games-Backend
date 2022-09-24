package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.tax.Tax;
import com.example.amazinggamesbackend.core.tax.TaxFactory;
import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.LoginCredentials;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import com.example.amazinggamesbackend.security.JWTUtil;
import org.hibernate.persister.spi.UnknownPersisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import java.util.Collections;
import java.util.Map;

@RestController // Marks the class a rest controller
@RequestMapping("/auth/") // Requests made to /api/auth/anything will be handles by this class
public class AuthController {

    // Injecting Dependencies
    @Autowired
    private UsersRepository repository;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody UserDTO user) {

        if (repository.findByUsernameIgnoreCase(user.getUsername()).isPresent() || repository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (user.getUsername().isBlank() || user.getPassword().isBlank() || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("This field can't be blank");
        } else {
            UserEntity registerUser = new UserEntity();
            registerUser.fromDTO(user);
            registerUser.setPassword(passwordEncoder.encode(user.getPassword()));
            registerUser.setRoles("ROLE_USER");
            registerUser.setCountry_id(user.getCountry_id());
            repository.save(registerUser);

            // Generating JWT
            String token = jwtUtil.generateToken(user.getEmail());

            // Responding with JWT
            return Collections.singletonMap("jwt-token" ,token);
        }
    }

    // Defining the function to handle the POST route for logging in a user
    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body) {
        try {
            // Creating the Authentication Token which will contain the credentials for authenticating
            // This token is used as input to the authentication process
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername() ,body.getPassword());

            // Authenticating the Login Credentials
            authManager.authenticate(authInputToken);

            // If this point is reached it means Authentication was successful
            // Generate the JWT
            String token = jwtUtil.generateToken(body.getUsername());

            // Respond with the JWT
            return Collections.singletonMap("jwt-token" ,token);
        } catch (AuthenticationException authExc) {
            // Auhentication Failed
            throw new RuntimeException("Invalid Login Credentials");
        }
    }


}
