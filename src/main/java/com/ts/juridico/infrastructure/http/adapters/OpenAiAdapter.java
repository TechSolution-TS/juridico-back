package com.ts.juridico.infrastructure.http.adapters;

import com.theokanning.openai.completion.chat.ChatMessage;
import com.ts.juridico.domain.port.OpenAiPort;
import com.ts.juridico.infrastructure.http.client.OpenAiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OpenAiAdapter implements OpenAiPort {

    private final OpenAiClient openAiClient;

    @Override
    public String summaryPetition(List<ChatMessage> messages) {
        return openAiClient.generateSummary(messages);
    }

    @Override
    public String generatePetition(String systemMessage, String userMessage) {
       return openAiClient.chamarModeloFineTuned(systemMessage, userMessage);
    }
}
