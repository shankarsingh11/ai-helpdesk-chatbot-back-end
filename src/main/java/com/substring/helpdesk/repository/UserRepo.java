package com.substring.helpdesk.repository;

import com.substring.helpdesk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    //  custom method
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPassword(String password);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username,String password);
}
