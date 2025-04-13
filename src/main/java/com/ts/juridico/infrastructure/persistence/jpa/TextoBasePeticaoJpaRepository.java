package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.TextoBasePeticao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TextoBasePeticaoJpaRepository  extends JpaRepository<TextoBasePeticao, Long> {

    Optional<TextoBasePeticao> findByLabelFundamentoJuridico(String fundamento);
}
