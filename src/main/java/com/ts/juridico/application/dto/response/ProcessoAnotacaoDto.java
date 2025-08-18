package com.ts.juridico.application.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessoAnotacaoDto {

    private String anotacaoUuid;
    private String titulo;
    private String texto;
    private String categoria;
    private String prioridade;
    private String dataCriacao;
    private String processoUuid;
}