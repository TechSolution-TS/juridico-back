package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.application.dto.request.AgendaRequestDto;
import com.ts.juridico.application.dto.response.AgendaResponseDto;
import com.ts.juridico.application.mapper.AgendaMapper;
import com.ts.juridico.domain.port.AgendaPort;
import com.ts.juridico.infrastructure.persistence.jpa.AgendaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AgendaAdapter implements AgendaPort {

    private final AgendaJpaRepository agendaJpaRepository;
    private final AgendaMapper agendaMapper;

    @Override
    public void save(AgendaRequestDto dto) {
        agendaJpaRepository.save(agendaMapper.toEntity(dto));
    }

    public List<AgendaResponseDto> findAll() {
        return agendaMapper.toListDto(agendaJpaRepository.findAll());
    }

    @Override
    public void deleteById(Long agendamentoId) {
        agendaJpaRepository.deleteById(agendamentoId);
    }
}
