package com.ts.juridico.application.controller;

import com.ts.juridico.domain.model.ArquivoModeloPeticao;
import com.ts.juridico.domain.service.GoogleDriveService;
import com.ts.juridico.domain.service.OpenAiChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
public class OpenAiController {

    private final OpenAiChatService openAiChatService;
    private final GoogleDriveService googleDriveService;

    @GetMapping("/{fileId}/summary")
    public ResponseEntity<String> getResumo(@PathVariable("fileId") String fileId) {
        ArquivoModeloPeticao arquivoModeloPeticao = googleDriveService.searchFileByFileId(fileId);
        String resumo = openAiChatService.resumePetitionChat(arquivoModeloPeticao);

        return ResponseEntity.ok(resumo);
    }
}
