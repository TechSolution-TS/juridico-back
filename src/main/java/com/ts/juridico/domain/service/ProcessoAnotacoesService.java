package com.ts.juridico.domain.service;

import com.ts.juridico.application.dto.response.ProcessoAnotacaoDto;
import com.ts.juridico.domain.model.ProcessoAnotacao;
import com.ts.juridico.domain.port.ProcessoAnotacaoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessoAnotacoesService {

    private final ProcessoAnotacaoPort processoAnotacaoPort;

    public List<ProcessoAnotacao> findByProcessoUuid(String processoUuid) {
        return processoAnotacaoPort.findByProcessoUuid(processoUuid);
    }

    public ProcessoAnotacao findByAnotacaoUuid(String processoUuid) {
        return processoAnotacaoPort.findByAnotacaoUuid(processoUuid);
    }

    @Transactional
    public ProcessoAnotacao saveNote(ProcessoAnotacaoDto dto) {
        return processoAnotacaoPort.saveAnotacao(dto);
    }

    @Transactional
    public void deleteByAnotacaoUuid(String anotacaoUuid) {
        processoAnotacaoPort.deleteNoteByAnotacaoUuid(anotacaoUuid);
    }

    @Transactional
    public void save(ProcessoAnotacao note) {
        processoAnotacaoPort.save(note);
    }
}
