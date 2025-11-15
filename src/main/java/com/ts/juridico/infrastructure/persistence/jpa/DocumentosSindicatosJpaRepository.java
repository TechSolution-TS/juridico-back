package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.DocumentosSindicatos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentosSindicatosJpaRepository extends JpaRepository<DocumentosSindicatos, Long> {
    void deleteByUuid(String uuid);
}
