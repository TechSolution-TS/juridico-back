package com.ts.juridico.application.controller;

import com.ts.juridico.application.dto.response.ArquivoModeloPeticaoDto;
import com.ts.juridico.application.dto.response.ProcessoDto;
import com.ts.juridico.application.dto.response.UploadResponseDto;
import com.ts.juridico.application.mapper.ProcessoMapper;
import com.ts.juridico.domain.model.*;
import com.ts.juridico.domain.service.GoogleDriveService;
import com.ts.juridico.domain.service.OpenAiChatService;
import com.ts.juridico.domain.service.ProcessoService;
import com.ts.juridico.domain.service.UsuarioService;
import com.ts.juridico.infrastructure.exception.FileStorageException;
import com.ts.juridico.infrastructure.persistence.mapper.ArquivoModeloPeticaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class ProcessoController {

    private final ProcessoService processoService;
    private final UsuarioService usuarioService;
    private final GoogleDriveService googleDriveService;
    private final ProcessoMapper processoMapper;
    private final ArquivoModeloPeticaoMapper arquivoModeloPeticaoMapper;
    private final OpenAiChatService openAiChatService;

    @PostMapping("/create/{fileId}")
    public ResponseEntity<String> createProcess(@PathVariable("fileId") String fileId) {
      try {
            UsuarioDocumento usuarioDocumento = usuarioService.searchDocumentById(fileId);
            ArquivoModeloPeticao arquivoModeloPeticao = googleDriveService.searchFileByFileId(fileId);
            String summary = openAiChatService.summaryPetitionChat(arquivoModeloPeticao);

            Processo processo = processoService.saveProcess(usuarioDocumento.getUserId(), summary);

            usuarioDocumento.setProcessUuid(processo.getProcessoUuid());
            usuarioService.updateDocumentProcessUser(usuarioDocumento);

            List<UsuarioDocumento> documentos = usuarioService.searchDocumentByUserId(usuarioDocumento.getUserId());
            documentos.forEach(doc -> {
                doc.setProcessUuid(processo.getProcessoUuid());
                usuarioService.updateDocumentProcessUser(doc);
            });

            arquivoModeloPeticao.setType("peticao");
            googleDriveService.updateFile(arquivoModeloPeticao);



            return ResponseEntity
                    .created(URI.create("/api/create/process"))
                    .body("Sucesso ao criar um novo processo!");
        } catch (Exception e) {
            throw new FileStorageException("Não foi possível criar processo!", e);
        }
    }

    @GetMapping()
    public ResponseEntity<List<ProcessoDto>> findAll() {
        List<Processo> processos = processoService.findAll();

        List<ProcessoDto> processoDtos = processoMapper.tolistDto(processos);
        processoDtos.forEach(p -> {
            UsuarioProcesso userProcess = usuarioService.findUserProcessById(p.getUserId());
            p.setNome(userProcess.getNome());
            p.setCpf(userProcess.getCpf());
        });

        return ResponseEntity
                .created(URI.create("/api/process"))
                .body(processoDtos);
    }

    @GetMapping("/infos/{processUuid}")
    public ResponseEntity<ProcessoDto> searchProcess(@PathVariable("processUuid") String processUuid) {
        Processo process = processoService.findByProcessUuid(processUuid);
        UsuarioProcesso userProcess = usuarioService.findUserProcessById(process.getUserId());
        ProcessoDto processoDto = processoMapper.dataToModel(process, userProcess);

        return ResponseEntity
                .created(URI.create("/api/process"))
                .body(processoDto);
    }

    @GetMapping("/{processUuid}/documents")
    public ResponseEntity<List<ArquivoModeloPeticaoDto>> searchDocumentsProcess(@PathVariable("processUuid") String processUuid) {
        List<ArquivoModeloPeticao> files = new ArrayList<>();
        List<UsuarioDocumento> usuarioDocumento = usuarioService.searchDocumentByProcessUuid(processUuid);

        usuarioDocumento.forEach(document -> {
            ArquivoModeloPeticao arquivoModeloPeticao = googleDriveService.searchFileByFileId(document.getFileId());
            files.add(arquivoModeloPeticao);
        });


        return ResponseEntity
                .created(URI.create("/api/process"))
                .body(arquivoModeloPeticaoMapper.tolistFundationDto(files));
    }

    @PostMapping("/upload/{processUuid}")
    public ResponseEntity<UploadResponseDto> uploadFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable(value = "processUuid") String processUuid) {
        try {
            String fileId = googleDriveService.uploadFile(file, "peticao");
            Processo process = processoService.findByProcessUuid(processUuid);

            usuarioService.saveDocumentProcessUser(process.getUserId(), fileId, processUuid);

            UploadResponseDto dto = new UploadResponseDto(fileId);
            return ResponseEntity
                    .created(URI.create("/api/files/" + fileId))
                    .body(dto);
        } catch (Exception e) {
            throw new FileStorageException("Não foi possível enviar arquivo", e);
        }
    }
}
