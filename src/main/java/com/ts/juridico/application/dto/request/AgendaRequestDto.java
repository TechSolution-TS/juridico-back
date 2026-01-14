package com.ts.juridico.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AgendaRequestDto {

    private String dataAgendamento;
    private String horarioAgendamento;
    private String titulo;
    private String descricao;
    private String numeroProcesso;
    private String advogados;
}
