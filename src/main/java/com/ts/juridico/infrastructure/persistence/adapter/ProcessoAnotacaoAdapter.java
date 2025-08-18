package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.application.dto.response.ProcessoAnotacaoDto;
import com.ts.juridico.application.mapper.ProcessoAnotacaoMapper;
import com.ts.juridico.domain.model.ProcessoAnotacao;
import com.ts.juridico.domain.port.ProcessoAnotacaoPort;
import com.ts.juridico.infrastructure.persistence.jpa.ProcessoAnotacaoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProcessoAnotacaoAdapter implements ProcessoAnotacaoPort {

    private final ProcessoAnotacaoJpaRepository processoAnotacaoJpaRepository;
    private final ProcessoAnotacaoMapper processoAnotacaoMapper;

    @Override
    public ProcessoAnotacao saveAnotacao(ProcessoAnotacaoDto dto) {
        return processoAnotacaoJpaRepository.save(processoAnotacaoMapper.dtoToModel(dto));
    }

    @Override
    public List<ProcessoAnotacao> findByProcessoUuid(String uuid) {
        return processoAnotacaoJpaRepository.findByProcessoUuid(uuid);
    }

    @Override
    public void deleteNoteByAnotacaoUuid(String anotacaoUuid) {
        processoAnotacaoJpaRepository.deleteByAnotacaoUuid(anotacaoUuid);
    }

    @Override
    public ProcessoAnotacao findByAnotacaoUuid(String anotacaoUuid) {
        return processoAnotacaoJpaRepository.findByAnotacaoUuid(anotacaoUuid);
    }

    @Override
    public void save(ProcessoAnotacao note) {
        processoAnotacaoJpaRepository.save(note);
    }
}
