package com.ts.juridico.application.mapper;

import com.ts.juridico.application.dto.response.FundamentoJuridicoDto;
import com.ts.juridico.application.dto.response.MotivoJuridicoDto;
import com.ts.juridico.application.dto.response.PeticaoDto;
import com.ts.juridico.domain.model.FundamentoJuridico;
import com.ts.juridico.domain.model.MotivoJuridico;
import com.ts.juridico.domain.model.Peticao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PeticaoFundamentoMotivoMapper {

    public List<PeticaoDto> tolistDto(List<Peticao> list) {
        return list.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    public PeticaoDto modelToDto(Peticao peticao) {
        return PeticaoDto.builder()
                .modelo(peticao.getModelo())
                .tipoPeticao(peticao.getTipoPeticao())
                .value(peticao.getValue())
                .build();
    }

    public List<FundamentoJuridicoDto> tolistFundationDto(List<FundamentoJuridico> list) {
        return list.stream()
                .map(this::modelToFundationDto)
                .collect(Collectors.toList());
    }

    public FundamentoJuridicoDto modelToFundationDto(FundamentoJuridico fundamentoJuridico) {
        return FundamentoJuridicoDto.builder()
                .hipotese(fundamentoJuridico.getHipotese())
                .value(fundamentoJuridico.getValue())
                .peticao(fundamentoJuridico.getPeticao())
                .build();
    }

    public List<MotivoJuridicoDto> tolistReasonDto(List<MotivoJuridico> list) {
        return list.stream()
                .map(this::modelToReasonDto)
                .collect(Collectors.toList());
    }

    public MotivoJuridicoDto modelToReasonDto(MotivoJuridico motivoJuridico) {
        return MotivoJuridicoDto.builder()
                .motivo(motivoJuridico.getMotivo())
                .labelFundamento(motivoJuridico.getLabelFundamento())
                .tituloMotivo(motivoJuridico.getTituloMotivo())
                .explicacao(motivoJuridico.getExplicacao())
                .fundamentoJuridico(motivoJuridico.getFundamentoJuridico())
                .build();
    }
}
