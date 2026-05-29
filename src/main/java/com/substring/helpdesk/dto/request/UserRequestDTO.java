package com.substring.helpdesk.dto.request;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRequestDTO {
    @NotNull
    private String username;
    @Size(min = 6)
    @NotNull
    private String password;
}
