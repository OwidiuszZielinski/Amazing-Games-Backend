package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.users.UserRepository;
import com.example.amazinggamesbackend.core.users.dto.UserDTO;
import com.example.amazinggamesbackend.core.users.model.LoginCredentials;
import com.example.amazinggamesbackend.core.users.model.User;
import com.example.amazinggamesbackend.security.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/")
public class AuthController {

    private final UserRepository repository;

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authManager;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@Valid @RequestBody UserDTO user) {

        if (repository.findByUsernameIgnoreCase(user.getUsername()).isPresent()
                || repository.findByEmail(user.getEmail()).isPresent()
                || repository.findByUsernameIgnoreCase(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (user.getUsername().isBlank() || user.getPassword().isBlank() || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("This field can't be blank");
        } else {
            User registerUser = new User();
            registerUser.fromDTO(user);
            registerUser.setPassword(passwordEncoder.encode(user.getPassword()));
            registerUser.setRoles("ROLE_USER");
            registerUser.setCountry_id(user.getCountry_id());
            repository.save(registerUser);

            String token = jwtUtil.generateToken(user.getEmail());


            return Collections.singletonMap("jwt-token", token);
        }
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@Valid @RequestBody LoginCredentials body) {
        try {

            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());
            authManager.authenticate(authInputToken);
            String token = jwtUtil.generateToken(body.getUsername());
            return Collections.singletonMap("jwt-token", token);
        } catch (AuthenticationException authExc) {
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

}
