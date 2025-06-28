package com.ts.juridico.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProcessoDto {

    private String processoUuid;
    private Long userId;
    private String nome;
    private String cpf;
    private String status;
    private String data;
    private String tribunal;
    private String advogado;
    private String telefone;
    private String gov;
    private String summary;
    private String numeroProcesso;
}
