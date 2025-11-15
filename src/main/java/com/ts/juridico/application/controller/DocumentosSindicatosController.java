package com.ts.juridico.application.controller;

import com.ts.juridico.application.dto.request.DocumentosSindicatosRequestDto;
import com.ts.juridico.application.dto.response.ArquivosDocumentosSindicatosReponseDto;
import com.ts.juridico.application.dto.response.AuthResponseDto;
import com.ts.juridico.application.dto.response.DocumentosSindicatosResponseDto;
import com.ts.juridico.application.dto.response.UploadResponseDto;
import com.ts.juridico.domain.model.ArquivosDocumentosSindicatos;
import com.ts.juridico.domain.service.DocumentosSindicatosService;
import com.ts.juridico.domain.service.GoogleDriveService;
import com.ts.juridico.infrastructure.exception.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/documentos-sindicatos")
@RequiredArgsConstructor
public class DocumentosSindicatosController {

    private final DocumentosSindicatosService documentosSindicatosService;
    private final GoogleDriveService driveService;

    @PostMapping("/create-folder")
    public ResponseEntity<?> createFolder(@RequestBody DocumentosSindicatosRequestDto dto) {

        try {
            documentosSindicatosService.createNewFolder(dto);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao tentar criar nova pasta d documentos sindicatos", e);
        }

        return ResponseEntity.ok("Operação realiza com sucesso!");
    }

    @GetMapping("/folders")
    public ResponseEntity<List<DocumentosSindicatosResponseDto>> searchFolders() {
        List<DocumentosSindicatosResponseDto> documentosSindicatosResponseDtos = documentosSindicatosService.searchFolders();
        return ResponseEntity.ok(documentosSindicatosResponseDtos);
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponseDto> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folderUuid", required = true) String folderUuid) {
        try {
            String fileId = driveService.uploadFileFolders(file, folderUuid);
            UploadResponseDto dto = new UploadResponseDto(fileId);

            return ResponseEntity
                    .created(URI.create("/api/documentos-sindicatos/upload"))
                    .body(dto);
        } catch (Exception e) {
            throw new FileStorageException("Não foi possível enviar arquivo documento sindicato", e);
        }
    }

    @GetMapping("/folders/files/{documentoSindicatoUuid}")
    public ResponseEntity<List<ArquivosDocumentosSindicatosReponseDto>> searchFiles(@PathVariable("documentoSindicatoUuid") String documentoSindicatoUuid) {
        List<ArquivosDocumentosSindicatosReponseDto> arquivosDocumentosSindicatos = documentosSindicatosService.searchFiles(documentoSindicatoUuid);
        return ResponseEntity.ok(arquivosDocumentosSindicatos);
    }

    @DeleteMapping("/folders/{uuid}")
    public ResponseEntity<?> deleteFolder(@PathVariable("uuid") String uuid) {
        documentosSindicatosService.deleteFolder(uuid);
        return ResponseEntity.ok("Operação realiza com sucesso!");
    }

    @DeleteMapping("/file/{fileUuid}")
    public ResponseEntity<?> deleteFile(@PathVariable("fileUuid") String fileUuid) {
        documentosSindicatosService.deleteFile(fileUuid);
        return ResponseEntity.ok("Operação realiza com sucesso!");
    }
}
