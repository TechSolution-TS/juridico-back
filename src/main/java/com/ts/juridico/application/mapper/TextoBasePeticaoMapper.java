package com.ts.juridico.application.mapper;

import com.ts.juridico.application.dto.response.TextoBasePeticaoDto;
import com.ts.juridico.domain.model.TextoBasePeticao;
import org.springframework.stereotype.Component;

@Component
public class TextoBasePeticaoMapper {

    public TextoBasePeticaoDto modelToDto(TextoBasePeticao entity) {
        return TextoBasePeticaoDto.builder()
                .labelFundamentoJuridico(entity.getLabelFundamentoJuridico())
                .enderecamento(entity.getEnderecamento())
                .qualificacaoReclamante(entity.getQualificacaoReclamante())
                .qualificacaoReclamado(entity.getQualificacaoReclamado())
                .tituloJusticaGratuita(entity.getTituloJusticaGratuita())
                .pedidoJusticaGratuita(entity.getPedidoJusticaGratuita())
                .tituloJuizoDigital(entity.getTituloJuizoDigital())
                .pedidoJuizoDigital(entity.getPedidoJuizoDigital())
                .tituloIncensaoHonorarios(entity.getTituloIncensaoHonorarios())
                .incensaoHonorarios(entity.getIncensaoHonorarios())
                .tituloContratoTrabalho(entity.getTituloContratoTrabalho())
                .contratoTrabalho(entity.getContratoTrabalho())
                .tituloHonorariosSucubencias(entity.getTituloHonorariosSucubencias())
                .honorariosSucubencias(entity.getHonorariosSucubencias())
                .tituloExibicaoDocumentos(entity.getTituloExibicaoDocumentos())
                .exibicaoDocumentos(entity.getExibicaoDocumentos())
                .tituloPedidos(entity.getTituloPedidos())
                .pedidos(entity.getPedidos())
                .build();
    }
}
