package com.ts.juridico.infrastructure.persistence.mapper;

import com.google.api.services.drive.model.File;
import com.ts.juridico.application.dto.response.ArquivoModeloPeticaoDto;
import com.ts.juridico.domain.model.ArquivoModeloPeticao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArquivoModeloPeticaoMapper {

    public ArquivoModeloPeticao fileToModel(File file, String typeFile) {
        return ArquivoModeloPeticao.builder()
                .nome(file.getName())
                .arquivoId(file.getId())
                .linkVisualizar(file.getWebViewLink())
                .linkDownload(file.getWebContentLink())
                .mimeType(file.getMimeType())
                .type(typeFile)
                .build();
    }

    public List<ArquivoModeloPeticaoDto> tolistFundationDto(List<ArquivoModeloPeticao> list) {
        return list.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    public ArquivoModeloPeticaoDto modelToDto(ArquivoModeloPeticao arquivoModeloPeticao) {
        return ArquivoModeloPeticaoDto.builder()
                .nome(arquivoModeloPeticao.getNome())
                .arquivoId(arquivoModeloPeticao.getArquivoId())
                .linkVisualizar(arquivoModeloPeticao.getLinkVisualizar())
                .linkDownload(arquivoModeloPeticao.getLinkDownload())
                .mimeType(arquivoModeloPeticao.getMimeType())
                .build();
    }
}
