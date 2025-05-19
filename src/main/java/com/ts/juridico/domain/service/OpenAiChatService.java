package com.ts.juridico.domain.service;

import com.theokanning.openai.completion.chat.ChatMessage;
import com.ts.juridico.domain.model.ArquivoModeloPeticao;
import com.ts.juridico.domain.port.OpenAiPort;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.tika.Tika;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiChatService {

    private final RestTemplate rest;
    private final Tika tika;
    private final OpenAiPort openAiPort;

    public String resumePetitionChat(ArquivoModeloPeticao arquivoModeloPeticao) {
        try {
            String text = rest.execute(
                    arquivoModeloPeticao.getLinkDownload(),
                    HttpMethod.GET,
                    null,
                    clientHttpResponse -> {
                        try (InputStream in = clientHttpResponse.getBody()) {
                            try {
                                return tika.parseToString(in);
                            } catch (TikaException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
            );

            // Prepara mensagens para prompt engineering
            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage("system",
                    "Você é um assistente jurídico. Sempre que receber o texto de uma petição, responda com um resumo de até 10 linhas, seguindo o formato: " +
                            "1. Identificação das Partes\n2. Objeto da Petição\n3. Fundamentação Jurídica\n4. Pedidos\n5. Observações Finais"));
            messages.add(new ChatMessage("user", text));

            return openAiPort.resumePetition(messages);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao extrair texto: " + e.getMessage(), e);
        }
    }
}
