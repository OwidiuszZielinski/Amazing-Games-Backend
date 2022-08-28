package com.example.amazinggamesbackend.security;

import com.example.amazinggamesbackend.core.users.UsersRepository;
import com.example.amazinggamesbackend.core.users.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userRes = repository.findByUsernameIgnoreCase(username);
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Could not findUser with username = " + username);

        UserEntity user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

    }
}
