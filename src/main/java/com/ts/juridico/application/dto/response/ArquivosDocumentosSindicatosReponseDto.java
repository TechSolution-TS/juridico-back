package com.ts.juridico.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArquivosDocumentosSindicatosReponseDto {

    private String nome;
    private String uuid;
    private String arquivoId;
    private String linkVisualizar;
    private String linkDownload;
    private String mimeType;
    private String documentoSindicatoUuid;
}
