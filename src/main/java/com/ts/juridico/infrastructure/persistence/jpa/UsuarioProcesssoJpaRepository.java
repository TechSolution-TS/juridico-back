package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.UsuarioProcesso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioProcesssoJpaRepository extends JpaRepository<UsuarioProcesso, Long> {
}
