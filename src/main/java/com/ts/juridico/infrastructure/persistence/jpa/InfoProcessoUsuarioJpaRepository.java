package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.InfoProcessoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoProcessoUsuarioJpaRepository extends JpaRepository<InfoProcessoUsuario, Long> {
}
