package com.ts.juridico.domain.port;

import com.ts.juridico.domain.model.Processo;

import java.util.List;

public interface ProcessoPort {

    Processo saveProcess(Long userId, String advogado, String summary, String tribunal, String processoUuid);
    List<Processo> findAll();
    Processo findByProcessUuid(String processUuid);
    Processo save(Processo processo);
    List<Processo> findByUserId(Long userId);
}
