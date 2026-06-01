package com.substring.helpdesk.service;

import com.substring.helpdesk.dto.request.AIRequestDTO;
import reactor.core.publisher.Flux;


public interface AIService {
    public String getResponseFromAssistant(AIRequestDTO aiRequestDTO);
    public Flux<String> streamResponseFromAssistant(AIRequestDTO aiRequestDTO);
}
