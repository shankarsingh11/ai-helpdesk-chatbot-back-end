package com.substring.helpdesk.service.ai;
import com.substring.helpdesk.dto.request.AIRequestDTO;
import com.substring.helpdesk.ai.tools.EmailTool;
import com.substring.helpdesk.ai.tools.TicketDatabaseTool;
import com.substring.helpdesk.exception.custom.AIServiceResponseException;
import com.substring.helpdesk.utils.validator.AIRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIServiceImpl implements AIService {

    @Qualifier("helpDeskChatClient")
    private final ChatClient chatClient;



    private final TicketDatabaseTool ticketDatabaseTool;
    private final EmailTool emailTool;
    private final AIRequestValidator validator;

    @Value("classpath:prompts/helpdesk-system.st")
    private Resource systemPromptResource;

    @Override
    public String getResponseFromAssistant(AIRequestDTO aiRequestDTO) {

        // validate request
        validator.validate(aiRequestDTO);
         // set value
        String query= aiRequestDTO.getQuery();
        String conversationId= getConversationId(aiRequestDTO);
        //logs
        log.info("Processing AI request. ConversationId: {}", conversationId);
        log.debug("User Query: {}", query);

        try{
            //basic call to llm model
           String aiResponse=invokeLLM(query,conversationId);
           return aiResponse;

        } catch (Exception ex) {

            log.error(
                    "User query request failed. ConversationId: {}, Error: {}",
                    conversationId,
                    ex.getMessage(),
                    ex
            );

            throw new AIServiceResponseException(
                    "Unable to process user query request at the moment.",
                    ex
            );
        }

    }

    @Override
    public Flux<String> streamResponseFromAssistant(AIRequestDTO aiRequestDTO) {

        //null check like you did in getResponseFromAssistant
        if (aiRequestDTO == null) return Flux.just("Please tell me how I can help you.");


        String query=aiRequestDTO.getQuery();
        String conversationId=getConversationId(aiRequestDTO);

        log.info("Query : {}", query);
        log.info("ConversationId : {}", conversationId);

        try{
                return this.chatClient
                        .prompt()
                        .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                        //tool information
                        .tools(ticketDatabaseTool, emailTool)
                        .system(systemPromptResource)
                        .user(query)
                        .stream().content();

            } catch (Exception e) {
            log.error("Error streaming calling chat client: {}", e.getMessage(), e);
            throw new RuntimeException("Streaming Chat call failed", e);
        }

    }


    //  invokeLLM method
    private String invokeLLM(String query,String conversationId){

        return chatClient
                .prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,conversationId))
                //tool information
                .tools(ticketDatabaseTool, emailTool)
                .system(systemPromptResource)
                .user(query)
                .call()
                .content();
    }

    // get conversationId  method
    private String getConversationId(
            AIRequestDTO request) {

        if (request.getConversationId() == null ||
                request.getConversationId().isBlank()) {

            String conversationId = UUID.randomUUID().toString();

            log.info(
                    "Generated new conversationId={}",
                    conversationId
            );

            return conversationId;
        }

        return request.getConversationId();
    }

}
