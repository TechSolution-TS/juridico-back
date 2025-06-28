package com.ts.juridico.application.controller;

import com.google.api.services.drive.model.File;
import com.ts.juridico.application.dto.response.ArquivoModeloPeticaoDto;
import com.ts.juridico.application.dto.response.UploadResponseDto;
import com.ts.juridico.domain.service.GoogleDriveService;
import com.ts.juridico.domain.service.UsuarioService;
import com.ts.juridico.infrastructure.exception.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private final GoogleDriveService driveService;
    private final UsuarioService usuarioService;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponseDto> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "typeFile", required = false) String typeFile) {
        try {
            String fileId = driveService.uploadFile(file, typeFile);

            if (StringUtils.hasText(cpf)) {
                usuarioService.addDocumentProcessUser(cpf, fileId);
            }

            UploadResponseDto dto = new UploadResponseDto(fileId);
            return ResponseEntity
                    .created(URI.create("/api/files/" + fileId))
                    .body(dto);
        } catch (Exception e) {
            log.error("Falha no upload do arquivo", e);
            throw new FileStorageException("Não foi possível enviar arquivo", e);
        }
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<ArquivoModeloPeticaoDto>> searchFiles(@PathVariable String type) {
        return ResponseEntity.ok(driveService.searchFiles(type));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {
        driveService.deleteFileByFileId(fileId);
        return ResponseEntity.ok("Operação realiza com sucesso!");
    }

    @GetMapping("/driver")
    public ResponseEntity<List<Map<String, String>>> listAll() {
        try {
            List<File> files = driveService.listFiles();
            List<Map<String, String>> dto = files.stream()
                    .map(f -> Map.of(
                            "id", f.getId(),
                            "name", f.getName(),
                            "webViewLink", f.getWebViewLink()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(List.of(Map.of("error", "Não foi possível listar: " + e.getMessage())));
        }
    }

    @GetMapping("/metadata/{fileId}")
    public ResponseEntity<Map<String, String>> metadata(@PathVariable String fileId) {
        try {
            File meta = driveService.getFileMetadata(fileId);
            return ResponseEntity.ok(Map.of(
                    "id", meta.getId(),
                    "name", meta.getName(),
                    "mimeType", meta.getMimeType(),
                    "webViewLink", meta.getWebViewLink(),
                    "downloadLink", meta.getWebContentLink()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Não foi possível obter metadata: " + e.getMessage()));
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String fileId) {
        try {
            byte[] data = driveService.downloadFile(fileId);

            // Recupera o tipo MIME do arquivo (opcional)
            String mimeType = driveService
                    .getFileMetadata(fileId)
                    .getMimeType();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileId + "\"")
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(new ByteArrayResource(data));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(null);
        }
    }

}