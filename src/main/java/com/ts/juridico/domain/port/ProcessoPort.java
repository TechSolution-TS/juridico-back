package com.ts.juridico.domain.port;

import com.ts.juridico.domain.model.Processo;

import java.util.List;

public interface ProcessoPort {

    Processo saveProcess(Long userId, String advogado, String summary);
    List<Processo> findAll();
    Processo findByProcessUuid(String processUuid);
}
