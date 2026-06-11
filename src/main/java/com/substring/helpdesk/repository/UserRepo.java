package com.substring.helpdesk.repository;

import com.substring.helpdesk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    //  custom method
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUsernameIgnoreCase(String username);

    Optional<User> findByUsernameIgnoreCaseOrEmailIgnoreCase(
            String username,
            String email
    );
}
