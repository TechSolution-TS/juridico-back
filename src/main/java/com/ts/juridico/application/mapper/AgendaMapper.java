package com.ts.juridico.application.mapper;

import com.ts.juridico.application.dto.request.AgendaRequestDto;
import com.ts.juridico.application.dto.response.AgendaResponseDto;
import com.ts.juridico.domain.model.Agenda;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AgendaMapper {

    public Agenda toEntity(AgendaRequestDto dto) {
        return Agenda.builder()
                .uuid(UUID.randomUUID().toString())
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .data(dto.getDataAgendamento())
                .horario(dto.getHorarioAgendamento())
                .numeroProcesso(dto.getNumeroProcesso())
                .advogados(dto.getAdvogados())
                .build();
    }

    public List<AgendaResponseDto> toListDto(List<Agenda> agendas) {
        return agendas.stream().map(agenda -> {
            return AgendaResponseDto.builder()
                    .id(agenda.getId())
                    .dataAgendamento(agenda.getData())
                    .horarioAgendamento(agenda.getHorario())
                    .titulo(agenda.getTitulo())
                    .descricao(agenda.getDescricao())
                    .numeroProcesso(agenda.getNumeroProcesso())
                    .advogados(agenda.getAdvogados())
                    .build();
        }).toList();
    }
}
