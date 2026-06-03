package com.substring.helpdesk.service.auth;

import com.substring.helpdesk.dto.request.LoginRequestDTO;
import com.substring.helpdesk.dto.request.RegisterRequestDTO;
import com.substring.helpdesk.dto.response.LoginResponseDTO;
import com.substring.helpdesk.dto.response.RegisterResponseDTO;

public interface UserService {
    RegisterResponseDTO createUser(RegisterRequestDTO registerRequestDTO);
    LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);

}
