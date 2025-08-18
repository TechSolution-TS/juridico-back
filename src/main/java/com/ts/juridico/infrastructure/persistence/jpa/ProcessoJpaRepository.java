package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessoJpaRepository extends JpaRepository<Processo, Long> {
    Optional<Processo> findByProcessoUuid(String processUuid);

    List<Processo> findByUserId(Long userId);

    List<Processo> findByAdvogado(String adv);
}
