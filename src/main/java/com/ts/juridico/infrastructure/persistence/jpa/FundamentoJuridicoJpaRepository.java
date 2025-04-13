package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.FundamentoJuridico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FundamentoJuridicoJpaRepository extends JpaRepository<FundamentoJuridico, Long> {
    List<FundamentoJuridico> findByPeticao_id(Long id);

    Optional<FundamentoJuridico> findByValue(String typeFoundation);
}
