package com.ts.juridico.application.mapper;

import com.google.api.services.drive.model.File;
import com.ts.juridico.application.dto.request.DocumentosSindicatosRequestDto;
import com.ts.juridico.application.dto.response.ArquivosDocumentosSindicatosReponseDto;
import com.ts.juridico.application.dto.response.DocumentosSindicatosResponseDto;
import com.ts.juridico.domain.model.ArquivosDocumentosSindicatos;
import com.ts.juridico.domain.model.DocumentosSindicatos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DocumentosSindicatosMapper {

    public DocumentosSindicatos toEntity(DocumentosSindicatosRequestDto dto) {
        return DocumentosSindicatos.builder()
                .name(dto.getName())
                .uuid(UUID.randomUUID().toString())
                .build();
    }

    public DocumentosSindicatosResponseDto toResponseDto(DocumentosSindicatos entity) {
        return DocumentosSindicatosResponseDto.builder()
                .name(entity.getName())
                .uuid(entity.getUuid())
                .build();
    }

    public List<DocumentosSindicatosResponseDto> toList(List<DocumentosSindicatos> folders) {
        return folders.stream()
                .map(this::toResponseDto).toList();
    }

    public ArquivosDocumentosSindicatos toEntityFile(File file, String uuidFolder) {
        return ArquivosDocumentosSindicatos.builder()
                .name(file.getName())
                .uuid(UUID.randomUUID().toString())
                .arquivoId(file.getId())
                .linkVisualizar(file.getWebViewLink())
                .linkDownload(file.getWebContentLink())
                .mimeType(file.getMimeType())
                .documentoSindicatoUuid(uuidFolder)
                .build();
    }

    public ArquivosDocumentosSindicatosReponseDto toFileDto(ArquivosDocumentosSindicatos file) {
        return ArquivosDocumentosSindicatosReponseDto.builder()
                .nome(file.getName())
                .uuid(file.getUuid())
                .arquivoId(file.getArquivoId())
                .linkVisualizar(file.getLinkVisualizar())
                .linkDownload(file.getLinkDownload())
                .mimeType(file.getMimeType())
                .documentoSindicatoUuid(file.getDocumentoSindicatoUuid())
                .build();
    }

    public List<ArquivosDocumentosSindicatosReponseDto> toListFilesResponse(List<ArquivosDocumentosSindicatos> files) {
        return files.stream()
                .map(this::toFileDto)
                .toList();
    }
}
