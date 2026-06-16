package com.substring.helpdesk.dto.request;

import com.substring.helpdesk.entity.enm.Priority;
import com.substring.helpdesk.entity.enm.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class TicketRequest {

    @NotNull(message = "summary is required")
    private String summary;

    private Priority priority;

    @NotNull(message = "category is required")
    private  String category;

    @NotNull(message = "description is required")
    private  String description;

    @NotNull(message = "email is required")
    @Email(message = "please provide a valid email format")
    private String email;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    private Status status;


}
