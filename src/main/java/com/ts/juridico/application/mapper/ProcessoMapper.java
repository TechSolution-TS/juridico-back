package com.ts.juridico.application.mapper;

import com.ts.juridico.application.dto.response.ProcessoDto;
import com.ts.juridico.domain.model.Processo;
import com.ts.juridico.domain.model.UsuarioProcesso;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProcessoMapper {

    public List<ProcessoDto> tolistDto(List<Processo> processos) {
        return processos.stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    public ProcessoDto modelToDto(Processo processo) {
        return ProcessoDto.builder()
                .userId(processo.getUserId())
                .processoUuid(processo.getProcessoUuid())
                .status(processo.getStatus())
                .data(processo.getDataProcesso().toString())
                .tribunal(processo.getTribunal() != null? processo.getTribunal() : " - ")
                .build();
    }

    public ProcessoDto dataToModel(Processo processo, UsuarioProcesso usuarioProcesso) {
        return ProcessoDto.builder()
                .userId(processo.getUserId())
                .processoUuid(processo.getProcessoUuid())
                .status(processo.getStatus())
                .data(processo.getDataProcesso().toString())
                .cpf(usuarioProcesso.getCpf())
                .nome(usuarioProcesso.getNome())
                .tribunal(processo.getTribunal() != null? processo.getTribunal() : " - ")
                .telefone(usuarioProcesso.getTelefone())
                .gov(usuarioProcesso.getSenhaGov())
                .advogado(processo.getAdvogado())
                .summary(processo.getSummary())
                .build();
    }
}
