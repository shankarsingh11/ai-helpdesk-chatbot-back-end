package com.substring.helpdesk.controller;

import com.substring.helpdesk.dto.request.AIRequestDTO;
import com.substring.helpdesk.service.impl.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/helpdesk")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class AIController {


    private  final AIService aiService;

    @PostMapping
    public ResponseEntity<String >  getResponseFromAssistant(@RequestBody AIRequestDTO aiRequestDTO){
        return ResponseEntity.ok(aiService.getResponseFromAssistant(aiRequestDTO));
    }

    @PostMapping(value = "/stream")
    public Flux<String> streamResponseFromAssistant(@RequestBody AIRequestDTO aiRequestDTO){
        return this.aiService.streamResponseFromAssistant(aiRequestDTO);
    }



}
