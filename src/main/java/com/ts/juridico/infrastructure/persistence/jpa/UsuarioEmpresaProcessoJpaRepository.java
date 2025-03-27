package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.UsuarioEmpresaProcesso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioEmpresaProcessoJpaRepository extends JpaRepository<UsuarioEmpresaProcesso, Long> {
}
