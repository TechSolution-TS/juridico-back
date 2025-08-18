package com.ts.juridico.application.controller;

import com.ts.juridico.application.dto.response.ArquivoModeloPeticaoDto;
import com.ts.juridico.application.dto.response.ProcessoAnotacaoDto;
import com.ts.juridico.application.dto.response.ProcessoDto;
import com.ts.juridico.application.dto.response.UploadResponseDto;
import com.ts.juridico.application.mapper.ProcessoAnotacaoMapper;
import com.ts.juridico.application.mapper.ProcessoMapper;
import com.ts.juridico.domain.model.*;
import com.ts.juridico.domain.service.*;
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
    private final ProcessoAnotacoesService processoAnotacoesService;
    private final ProcessoAnotacaoMapper processoAnotacaoMapper;

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
    public ResponseEntity<List<ProcessoDto>> findAll(@RequestParam(value = "adv", required = false) String adv) {
        List<Processo> processos = (adv != null && !adv.isBlank())
                ? processoService.findByAdvogado(adv)
                : processoService.findAll();

        List<ProcessoDto> processoDtos = processoMapper.tolistDto(processos);

        processoDtos.forEach(p -> {
            UsuarioProcesso userProcess = usuarioService.findUserProcessById(p.getUserId());
            p.setNome(userProcess.getNome());
            p.setCpf(userProcess.getCpf());
        });

        return ResponseEntity
                .ok(processoDtos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProcessoDto>> searchProcess(@PathVariable("userId") Long userId) {
        List<ProcessoDto> processos = new ArrayList<>();

        List<Processo> process = processoService.findByUserId(userId);
        UsuarioProcesso userProcess = usuarioService.findUserProcessById(process.get(0).getUserId());

        process.forEach(p -> {
            processos.add(processoMapper.dataToModel(p, userProcess));
        });

        return ResponseEntity
                .created(URI.create("/api/process"))
                .body(processos);
    }

    @GetMapping("/infos/{processUuid}")
    public ResponseEntity<ProcessoDto> searchProcess(@PathVariable("processUuid") String processUuid) {
        Processo process = processoService.findByProcessUuid(processUuid);
        UsuarioProcesso userProcess = usuarioService.findUserProcessById(process.getUserId());
        ProcessoDto processoDto = processoMapper.dataToModel(process, userProcess);

        return ResponseEntity
                .ok(processoDto);
    }

    @GetMapping("/notes/{processUuid}")
    public ResponseEntity<List<ProcessoAnotacaoDto>> searchNotesProcess(@PathVariable("processUuid") String processUuid) {
        List<ProcessoAnotacao> notes = processoAnotacoesService.findByProcessoUuid(processUuid);
        List<ProcessoAnotacaoDto> notesDto = processoAnotacaoMapper.toListDto(notes);

        return ResponseEntity
                .ok(notesDto);
    }

    @PostMapping("/notes/create")
    public ResponseEntity<ProcessoAnotacaoDto> createNotes(@RequestBody ProcessoAnotacaoDto dto) {
        try {
            ProcessoAnotacao note = processoAnotacoesService.saveNote(dto);

            return ResponseEntity
                    .created(URI.create("/api/notes/create"))
                    .body(processoAnotacaoMapper.modelToDto(note));

        } catch (Exception e) {
            throw new FileStorageException("Não foi possível criar a anotação!", e);
        }
    }

    @PutMapping("/notes/update")
    public ResponseEntity<ProcessoAnotacaoDto> updateNote(@RequestBody ProcessoAnotacaoDto dto) {
        try {
            ProcessoAnotacao note = processoAnotacoesService.findByAnotacaoUuid(dto.getAnotacaoUuid());
            note.setTexto(dto.getTexto());

            processoAnotacoesService.save(note);

            return ResponseEntity
                    .created(URI.create("/api/notes/update"))
                    .body(processoAnotacaoMapper.modelToDto(note));

        } catch (Exception e) {
            throw new FileStorageException("Não foi possível atualizar a anotação!", e);
        }
    }

    @DeleteMapping("/notes/{anotacaoUuid}")
    public ResponseEntity<String> deleteNote(@PathVariable String anotacaoUuid) {
        processoAnotacoesService.deleteByAnotacaoUuid(anotacaoUuid);
        return ResponseEntity.ok("Operação realiza com sucesso!");
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
                .ok(arquivoModeloPeticaoMapper.tolistFundationDto(files));
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
