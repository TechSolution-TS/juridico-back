package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.MotivoJuridico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotivoJuridicoJpaRepository extends JpaRepository<MotivoJuridico, Long> {
    List<MotivoJuridico> findByFundamentoJuridico_id(Long id);
}
