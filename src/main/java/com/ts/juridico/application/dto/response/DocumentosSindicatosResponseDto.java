package com.ts.juridico.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class DocumentosSindicatosResponseDto {

    private String name;
    private String uuid;
}
