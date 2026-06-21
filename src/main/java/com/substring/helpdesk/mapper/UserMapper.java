package com.substring.helpdesk.mapper;

import com.substring.helpdesk.dto.response.LoginResponse;
import com.substring.helpdesk.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public LoginResponse toDto(User user) {
      return   LoginResponse.builder()
                .message("")
                .build();
    }
}
