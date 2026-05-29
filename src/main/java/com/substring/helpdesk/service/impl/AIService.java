package com.substring.helpdesk.service.impl;
import com.substring.helpdesk.dto.request.AIRequestDTO;
import com.substring.helpdesk.entity.Query_ConversationID;
import com.substring.helpdesk.service.I_AIService;
import com.substring.helpdesk.tools.EmailTool;
import com.substring.helpdesk.tools.TicketDatabaseTool;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class AIService implements I_AIService {

    private final ChatClient chatClient;
    private final TicketDatabaseTool ticketDatabaseTool;
    private final EmailTool emailTool;

    @Value("classpath:/helpdesk-system.st")
    private Resource systemPromptResource;

    @Override
    public String getResponseFromAssistant(AIRequestDTO aiRequestDTO) {
        if (aiRequestDTO==null)return "Please Tell me how can you.. ";

        Query_ConversationID query_conversationID=new Query_ConversationID();

        // set query
        query_conversationID.setQuery(aiRequestDTO.getQuery());
        query_conversationID.setConversation_id(aiRequestDTO.getConversation_Id());

        try{
            //basic call to llm
            return this.chatClient
                    .prompt()
                    .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, query_conversationID.getConversation_id()))
                    //tool information
                    .tools(ticketDatabaseTool, emailTool)
                    .system(systemPromptResource)
                    .user(query_conversationID.getQuery())
                    .call()
                    .content();

        } catch (Exception e) {

            throw new RuntimeException("Chat client not call");

        }

    }

    @Override
    public Flux<String> streamResponseFromAssistant(AIRequestDTO aiRequestDTO) {

        Query_ConversationID query_conversationID=new Query_ConversationID();

        // set query
        query_conversationID.setQuery(aiRequestDTO.getQuery());
        query_conversationID.setConversation_id(aiRequestDTO.getConversation_Id());

        String query= query_conversationID.getQuery();
        String conversationId=query_conversationID.getConversation_id();

        log.info("Query :",query);
        log.info("ConversationId :", conversationId );

        try{
                return this.chatClient
                        .prompt()
                        .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, query_conversationID.getConversation_id()))
                        //tool information
                        .tools(ticketDatabaseTool, emailTool)
                        .system(systemPromptResource)
                        .user(query_conversationID.getQuery())
                        .stream().content();

            } catch (RuntimeException e) {
                throw new RuntimeException("Stream response error");
            }

    }

}
