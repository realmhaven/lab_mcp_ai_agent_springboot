package com.example.agent.config;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import com.example.agent.agent.BacklogAgent;
import com.example.agent.tools.AgentTool;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("ci")
public class CiChatModelConfig {

    @Bean
    public AnthropicChatModel ciChatModel() {
        return AnthropicChatModel.builder()
                .apiKey("ci-dummy-key")
                .modelName("claude-opus-4-20250514")
                .build();
    }

    @Bean
    public BacklogAgent backlogAgent(AnthropicChatModel model, List<AgentTool> tools) {
        return AiServices.builder(BacklogAgent.class)
                .chatModel(model)
                .tools(tools.toArray())
                .build();
    }
}
