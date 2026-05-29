package com.substring.helpdesk.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AIRequestDTO {
    @NotNull(message = "Query cannot be empty")
    String query;
    String conversation_Id;
}
