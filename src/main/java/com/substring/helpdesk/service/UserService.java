package com.substring.helpdesk.service;

import com.substring.helpdesk.dto.request.UserRequestDTO;

public interface UserService {
    String saveUser(UserRequestDTO userRequestDTO);
}
