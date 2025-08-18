package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.ProcessoAnotacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessoAnotacaoJpaRepository extends JpaRepository<ProcessoAnotacao, Long> {

    List<ProcessoAnotacao> findByProcessoUuid(String uuid);
    void deleteByAnotacaoUuid(String anotacaoUuid);
    ProcessoAnotacao findByAnotacaoUuid(String anotacaoUuid);
}
