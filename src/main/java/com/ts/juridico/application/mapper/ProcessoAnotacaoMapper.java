package com.ts.juridico.application.mapper;

import com.ts.juridico.application.dto.response.ProcessoAnotacaoDto;
import com.ts.juridico.domain.model.ProcessoAnotacao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProcessoAnotacaoMapper {

    public List<ProcessoAnotacaoDto> toListDto(List<ProcessoAnotacao> anotacoes) {
        return anotacoes.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    public ProcessoAnotacaoDto modelToDto(ProcessoAnotacao anotacao) {
        return ProcessoAnotacaoDto.builder()
                .anotacaoUuid(anotacao.getAnotacaoUuid())
                .titulo(anotacao.getTitulo())
                .texto(anotacao.getTexto())
                .categoria(anotacao.getCategoria())
                .prioridade(anotacao.getPrioridade())
                .dataCriacao(anotacao.getDataCriacao().toString())
                .processoUuid(anotacao.getProcessoUuid())
                .build();
    }

    public ProcessoAnotacao dtoToModel(ProcessoAnotacaoDto dto) {
        return ProcessoAnotacao.builder()
                .anotacaoUuid(UUID.randomUUID().toString())
                .titulo(dto.getTitulo())
                .texto(dto.getTexto())
                .categoria(dto.getCategoria())
                .prioridade(dto.getPrioridade())
                .processoUuid(dto.getProcessoUuid())
                .build();
    }
}
