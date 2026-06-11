package com.substring.helpdesk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AiConfig {

    @Value("${ai.memory.max-messages}")
    private int maxMessages;

    private static final String System_Prompt = """
            You are Liza, a professional Help Desk Assistant for Substring Technologies.
            Guidelines:
            - Be polite and professional.
            - Collect missing information before answering.
            - Provide clear troubleshooting steps.
            - Keep responses concise.
            - Summarize answers within 400 words.
            """;

    // chat memory ko create kar sakte hai
    // store chats
    @Bean
    public MessageWindowChatMemory chatMemory(
            JdbcChatMemoryRepository repository) {

        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(maxMessages)
                .build();
    }

    // ollama chat client
    @Bean("helpDeskChatClient")
    public ChatClient chatClient(ChatClient.Builder builder,  MessageWindowChatMemory chatMemory) {

        log.info("ChatClient bean created.");
        log.info("chat memory bean created. {}", chatMemory.getClass().getName());

        return builder
                .defaultSystem(System_Prompt)
                .defaultAdvisors(new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }

    //further more chat client implement in future

    // openAI chat client
    // gemini chat client


}
