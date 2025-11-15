package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.ArquivosDocumentosSindicatos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArquivosDocumentosSindicatosJpaRepository extends JpaRepository<ArquivosDocumentosSindicatos, Long> {
    List<ArquivosDocumentosSindicatos> findByDocumentoSindicatoUuid(String documentoSindicatoUuid);
    void deleteByUuid(String fileUuid);
    void deleteByDocumentoSindicatoUuid(String documentoSindicatoUuid);
}
