package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.FundamentoJuridico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundamentoJuridicoJpaRepository extends JpaRepository<FundamentoJuridico, Long> {
    List<FundamentoJuridico> findByPeticao_id(Long id);
}
