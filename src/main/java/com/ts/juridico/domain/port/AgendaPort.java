package com.ts.juridico.domain.port;

import com.ts.juridico.application.dto.request.AgendaRequestDto;
import com.ts.juridico.application.dto.response.AgendaResponseDto;

import java.util.List;

public interface AgendaPort {

    void save(AgendaRequestDto dto);
    List<AgendaResponseDto> findAll();
    void deleteById(Long agendamentoId);
}
