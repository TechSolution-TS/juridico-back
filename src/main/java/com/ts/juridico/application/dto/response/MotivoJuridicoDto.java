package com.ts.juridico.application.dto.response;

import com.ts.juridico.domain.model.FundamentoJuridico;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class MotivoJuridicoDto {

    private String motivo;
    private String explicacao;
    private FundamentoJuridico fundamentoJuridico;
}
