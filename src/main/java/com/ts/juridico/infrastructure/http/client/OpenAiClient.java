package com.ts.juridico.infrastructure.http.client;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiClient {

    private final OpenAiService openAi;

    public String generateResume(List<ChatMessage> messages) {
        // Chama OpenAI Chat Completion
        ChatCompletionRequest req = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .maxTokens(500)
                .temperature(0.2)
                .build();

        ChatCompletionResult result = openAi.createChatCompletion(req);
        return result.getChoices().get(0).getMessage().getContent().trim();
    }
}
