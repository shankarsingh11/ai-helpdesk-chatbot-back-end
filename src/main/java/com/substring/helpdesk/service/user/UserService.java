package com.substring.helpdesk.service.user;

import com.substring.helpdesk.dto.request.LoginRequest;
import com.substring.helpdesk.dto.request.RegisterRequest;
import com.substring.helpdesk.dto.response.LoginResponse;
import com.substring.helpdesk.dto.response.RegisterResponse;

public interface UserService {
    RegisterResponse createUser(RegisterRequest registerRequest);
    LoginResponse loginUser(LoginRequest loginRequestDTO);

}
