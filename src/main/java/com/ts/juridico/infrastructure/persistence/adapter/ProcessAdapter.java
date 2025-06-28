package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.domain.model.Processo;
import com.ts.juridico.domain.port.ProcessoPort;
import com.ts.juridico.infrastructure.persistence.jpa.ProcessoJpaRepository;
import com.ts.juridico.infrastructure.persistence.mapper.ProcessMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProcessAdapter implements ProcessoPort {

    private final ProcessoJpaRepository processoJpaRepository;
    private final ProcessMapper processMapper;

    @Override
    public Processo saveProcess(Long userId, String advogado, String summary, String tribunal, String processoUuid) {
        return processoJpaRepository.save(processMapper.dataToModel(userId, "pendente", advogado, summary, tribunal, processoUuid));
    }

    @Override
    public List<Processo> findAll() {
        return processoJpaRepository.findAll();
    }

    @Override
    public Processo findByProcessUuid(String processUuid) {
        return processoJpaRepository.findByProcessoUuid(processUuid)
                .orElseThrow(() ->  new RuntimeException("Processo com UUID inexistente!"));
    }

    @Override
    public Processo save(Processo processo) {
        return processoJpaRepository.save(processo);
    }
}
