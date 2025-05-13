package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.ArquivoModeloPeticao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArquivoModeloPeticaoJpaRepository extends JpaRepository<ArquivoModeloPeticao, Long> {

    List<ArquivoModeloPeticao> findByType(String typeFile);
    ArquivoModeloPeticao findByArquivoId(String arquivoId);
}
