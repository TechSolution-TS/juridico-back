package com.ts.juridico.infrastructure.http.client;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class OpenAiClient {

    private static final Logger log = LoggerFactory.getLogger(OpenAiClient.class);
    private final OpenAiService openAi;

    public String generateSummary(List<ChatMessage> messages) {
        try {
            log.info("Iniciando geração de resumo com {} mensagens", messages.size());

            ChatCompletionRequest req = ChatCompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .messages(messages)
                    .maxTokens(500)
                    .temperature(0.2)
                    .build();

            log.debug("Request para OpenAI: {}", req);

            ChatCompletionResult result = openAi.createChatCompletion(req);

            if (result.getChoices().isEmpty()) {
                log.warn("Resposta da OpenAI não retornou choices.");
                return "[Resumo indisponível: resposta sem conteúdo]";
            }

            String content = result.getChoices().get(0).getMessage().getContent().trim();
            log.info("Resumo gerado com sucesso: {}...", content.length() > 80 ? content.substring(0, 80) + "..." : content);

            return content;

        } catch (Exception e) {
            log.error("Erro ao chamar OpenAI API: {}", e.getMessage(), e);
            return "[Erro ao gerar resumo: " + e.getMessage() + "]";
        }
    }
}
