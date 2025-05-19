package com.ts.juridico.domain.port;

import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.List;

public interface OpenAiPort {

    String resumePetition(List<ChatMessage> messages);
}
