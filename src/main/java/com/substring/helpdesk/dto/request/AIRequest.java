package com.substring.helpdesk.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AIRequest {

    @NotNull(message = "query is required")
    String query;
    String conversationId;
}
