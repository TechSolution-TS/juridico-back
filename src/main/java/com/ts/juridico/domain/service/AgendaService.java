package com.ts.juridico.domain.service;

import com.ts.juridico.application.dto.request.AgendaRequestDto;
import com.ts.juridico.application.dto.response.AgendaResponseDto;
import com.ts.juridico.domain.port.AgendaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaPort agendaPort;

    public void createAgenda(AgendaRequestDto dto) {
        agendaPort.save(dto);
    }

    public List<AgendaResponseDto> searchAppointments() {
        return agendaPort.findAll();
    }

    public void deleteAppointments(Long agendamentoId) {
        agendaPort.deleteById(agendamentoId);
    }
}
