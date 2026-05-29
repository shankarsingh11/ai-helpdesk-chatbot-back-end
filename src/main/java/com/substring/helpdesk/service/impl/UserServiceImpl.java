package com.substring.helpdesk.service.impl;

import com.substring.helpdesk.dto.request.UserRequestDTO;
import com.substring.helpdesk.entity.User;
import com.substring.helpdesk.repository.UserRepo;
import com.substring.helpdesk.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public String saveUser(UserRequestDTO userRequestDTO) {

        //logs
        log.info("UserRequestDTO Password: ", userRequestDTO.getUsername().trim());
        log.info("UserRequestDTO Username: ", userRequestDTO.getPassword());

        // User Object created
        User user = new User();

        // Validates
        if (userRequestDTO.getUsername() == null || userRequestDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (userRequestDTO.getPassword() == null || userRequestDTO.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        String username = userRequestDTO.getUsername().trim();
        String password = userRequestDTO.getPassword();

        log.info("Username: ", username);
        log.info("Password: ", password);

        // Normalize -> set userRequestDto into user object
        user.setUsername(username);
        user.setPassword(password);

        // password encoded here
        //user.setPassword(encoder.encode(user.getPassword()));

        log.info("Encoded Password: ", user.getPassword());
        // JPA->save method call
        userRepo.save(user);
        return "user register successfully";
    }
}
