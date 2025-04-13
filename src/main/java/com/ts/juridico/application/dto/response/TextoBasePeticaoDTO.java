package com.ts.juridico.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TextoBasePeticaoDTO {

    private String labelFundamentoJuridico;
    private String enderecamento;
    private String qualificacaoReclamante;
    private String qualificacaoReclamado;
    private String tituloJusticaGratuita;
    private String pedidoJusticaGratuita;
    private String tituloJuizoDigital;
    private String pedidoJuizoDigital;
    private String tituloIncensaoHonorarios;
    private String incensaoHonorarios;
    private String tituloContratoTrabalho;
    private String contratoTrabalho;
    private String tituloHonorariosSucubencias;
    private String honorariosSucubencias;
    private String tituloExibicaoDocumentos;
    private String exibicaoDocumentos;
    private String tituloPedidos;
    private String pedidos;
}
