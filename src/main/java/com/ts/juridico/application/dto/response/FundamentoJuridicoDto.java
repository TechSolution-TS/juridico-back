package com.ts.juridico.application.dto.response;

import com.ts.juridico.domain.model.Peticao;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class FundamentoJuridicoDto {

    private String hipotese;
    private String value;
    private Peticao peticao;
}
