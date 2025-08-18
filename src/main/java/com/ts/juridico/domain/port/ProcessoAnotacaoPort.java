package com.ts.juridico.domain.port;

import com.ts.juridico.application.dto.response.ProcessoAnotacaoDto;
import com.ts.juridico.domain.model.ProcessoAnotacao;

import java.util.List;

public interface ProcessoAnotacaoPort {

    ProcessoAnotacao saveAnotacao(ProcessoAnotacaoDto dto);
    List<ProcessoAnotacao> findByProcessoUuid(String uuid);
    void deleteNoteByAnotacaoUuid(String anotacaoUuid);
    ProcessoAnotacao findByAnotacaoUuid(String processoUuid);
    void save(ProcessoAnotacao note);
}
