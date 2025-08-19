package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.ArquivoModeloPeticao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArquivoModeloPeticaoJpaRepository extends JpaRepository<ArquivoModeloPeticao, Long> {

    List<ArquivoModeloPeticao> findByType(String typeFile);
    ArquivoModeloPeticao findByArquivoId(String arquivoId);
    @Modifying
    @Transactional
    @Query("""
        UPDATE ArquivoModeloPeticao a
        SET a.type = :newType, a.status = :status, a.advogado = :newType
        WHERE a.arquivoId = :arquivoId
        """)
    int updateTypeByArquivoId(String arquivoId, String newType, String status);

    List<ArquivoModeloPeticao> findByAdvogado(String advogado);
}
