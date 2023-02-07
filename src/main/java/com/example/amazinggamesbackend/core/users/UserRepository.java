package com.example.amazinggamesbackend.core.users;

import com.example.amazinggamesbackend.core.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsernameIgnoreCase(String username);
    Optional<User> findByEmail(String username);

    Optional<User> findById(int id);
}
