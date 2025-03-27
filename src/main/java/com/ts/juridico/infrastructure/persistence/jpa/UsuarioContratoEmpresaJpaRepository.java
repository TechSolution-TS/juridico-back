package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.UsuarioContratoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioContratoEmpresaJpaRepository extends JpaRepository<UsuarioContratoEmpresa, Long> {
}
