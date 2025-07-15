package com.ts.juridico.infrastructure.http.client;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theokanning.openai.completion.chat.ChatMessageRole;

@Service
@RequiredArgsConstructor
public class OpenAiClient {

    private static final Logger log = LoggerFactory.getLogger(OpenAiClient.class);
    private final OpenAiService openAi;
    private static final String FINE_TUNED_MODEL = "ft:gpt-3.5-turbo-0125:techsolution::Bq8hUu4q";

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

    public String chamarModeloFineTuned(String systemMessage, String userMessage) {
        try {
            log.info("🚀 Chamando modelo fine-tuned: {}", FINE_TUNED_MODEL);

            List<ChatMessage> messages = Arrays.asList(
                    new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage),
                    new ChatMessage(ChatMessageRole.USER.value(), userMessage)
            );

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(FINE_TUNED_MODEL)
                    .messages(messages)
                    .maxTokens(3000) // Reduzido para evitar timeout
                    .temperature(0.1)
                    .topP(0.95)
                    .frequencyPenalty(0.1)
                    .presencePenalty(0.1)
                    .build();

            log.info("⏱️ Iniciando requisição (timeout: 180s)...");

            ChatCompletionResult result = openAi.createChatCompletion(request);

            if (result.getChoices().isEmpty()) {
                log.error("❌ Resposta sem choices do modelo fine-tuned");
                throw new RuntimeException("Resposta vazia do modelo fine-tuned");
            }

            String response = result.getChoices().get(0).getMessage().getContent();
            log.info("✅ Resposta recebida com {} caracteres", response.length());

            // Verificar se a resposta contém templates/placeholders
            if (response.contains("${") || response.contains("${{")) {
                log.warn("⚠️ RESPOSTA CONTÉM PLACEHOLDERS DE TEMPLATE");
            }

            return response;

        } catch (Exception e) {
            log.error("❌ Erro ao chamar modelo fine-tuned: {}", e.getMessage());

            // Verificar se é timeout e sugerir solução
            if (e.getMessage().contains("timeout") || e.getMessage().contains("SocketTimeoutException")) {
                log.error("⏱️ TIMEOUT DETECTADO - Modelo fine-tuned pode estar sobrecarregado");
                log.info("💡 Sugestão: O processamento está funcionando mas demorou mais que 180s");
                throw new RuntimeException("Timeout na geração da petição. O modelo está funcionando mas demorou mais que o esperado. Tente novamente em alguns segundos.", e);
            }

            throw new RuntimeException("Erro ao gerar petição trabalhista: " + e.getMessage(), e);
        }
    }

    // MÉTODO PARA TESTAR COM TIMEOUT MENOR
    public String testarComTimeoutCurto(String systemMessage, String userMessage) {
        try {
            log.info("🧪 Testando com configurações otimizadas...");

            List<ChatMessage> messages = Arrays.asList(
                    new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage),
                    new ChatMessage(ChatMessageRole.USER.value(), userMessage)
            );

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(FINE_TUNED_MODEL)
                    .messages(messages)
                    .maxTokens(2000) // Menor para resposta mais rápida
                    .temperature(0.0) // Zero para resposta mais direta
                    .build();

            ChatCompletionResult result = openAi.createChatCompletion(request);
            return result.getChoices().get(0).getMessage().getContent();

        } catch (Exception e) {
            log.error("❌ Erro no teste otimizado: {}", e.getMessage());
            throw new RuntimeException("Erro no teste: " + e.getMessage(), e);
        }
    }
}