package com.ts.juridico.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GerarPeticaoRequestDto {
    private String tituloFundamentoMotivo;
    private String explicacaoFundamentoMotivo;

    private String tituloVerbasRescisorias;
    private String explicacaoVerbasRescisorias;

    private String tituloDireitoSolicitado;
    private List<DireitoExplicacaoDto> explicacaoDireitoSolicitado;
}
