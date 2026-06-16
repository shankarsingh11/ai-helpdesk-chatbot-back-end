package com.substring.helpdesk.service.ai;

import com.substring.helpdesk.dto.request.AIRequest;
import reactor.core.publisher.Flux;


public interface AIService {
    public String getResponseFromAssistant(AIRequest aiRequest);
    public Flux<String> streamResponseFromAssistant(AIRequest aiRequest);
}
