package com.substring.helpdesk.dto.request;

import com.substring.helpdesk.entity.enm.Priority;
import com.substring.helpdesk.entity.enm.Status;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
public class TicketRequestDTO {

    @NotNull
    private String summary;
    private Priority priority;
    @NotNull
    private  String category;
    @NotNull
    private  String description;

    @NotNull
    private String email;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    private Status status;


}
