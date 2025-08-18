package com.ts.juridico.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArquivoModeloPeticaoDto {

    private String nome;
    private String arquivoId;
    private String linkVisualizar;
    private String linkDownload;
    private String mimeType;
    private String status;
    private String advogado;
}
