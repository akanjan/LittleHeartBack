package com.ecom.repositories;

import com.ecom.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    @Query("SELECT COUNT(u) FROM User u")
    Long countUsers();
}
