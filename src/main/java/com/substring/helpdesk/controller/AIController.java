package com.substring.helpdesk.controller;

import com.substring.helpdesk.dto.request.AIRequest;
import com.substring.helpdesk.service.ai.AIServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/ai/chat")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class AIController {

    private  final AIServiceImpl aiServiceImpl;

//helpdesk api
    @PostMapping
    public ResponseEntity<String > askQuestion(@Valid @RequestBody AIRequest aiRequest){
        System.out.println("AIService Request: "+ aiRequest);
        return ResponseEntity.ok(aiServiceImpl.getResponseFromAssistant(aiRequest));
    }
//stream chat api
    @PostMapping(value = "/stream")
    public Flux<String> streamResponseFromAssistant(@RequestBody AIRequest aiRequest){
        System.out.println("Stream AIService Request: "+aiServiceImpl);
        return this.aiServiceImpl.streamResponseFromAssistant(aiRequest);
    }



}
