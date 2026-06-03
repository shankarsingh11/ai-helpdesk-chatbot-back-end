package com.substring.helpdesk.controller;

import com.substring.helpdesk.dto.request.AIRequestDTO;
import com.substring.helpdesk.service.ai.AIServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/helpdesk")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class AIController {


    private  final AIServiceImpl aiServiceImpl;
// helpdesk api
    @PostMapping
    public ResponseEntity<String > askQuestion(@Valid @RequestBody AIRequestDTO aiRequestDTO){
        System.out.println("AIService Request: "+aiRequestDTO);
        return ResponseEntity.ok(aiServiceImpl.getResponseFromAssistant(aiRequestDTO));
    }
// stream chat api
    @PostMapping(value = "/stream")
    public Flux<String> streamResponseFromAssistant(@RequestBody AIRequestDTO aiRequestDTO){
        System.out.println("Stream AIService Request: "+aiServiceImpl);
        return this.aiServiceImpl.streamResponseFromAssistant(aiRequestDTO);
    }



}
