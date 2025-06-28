package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.UsuarioDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioDocumentoJpaRepository extends JpaRepository<UsuarioDocumento, Long> {

    Optional<UsuarioDocumento> findByFileId(String fileId);

    List<UsuarioDocumento> findByProcessUuid(String processUuid);
    List<UsuarioDocumento> findAllByUserIdAndProcessUuidNull(Long userId);
}
