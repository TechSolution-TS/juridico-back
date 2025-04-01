package com.ts.juridico.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PeticaoDto {

    private String tipoPeticao;
    private String modelo;
    private String value;
}
