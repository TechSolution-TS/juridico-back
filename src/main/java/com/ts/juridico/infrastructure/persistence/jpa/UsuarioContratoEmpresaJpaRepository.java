package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.UsuarioContratoEmpresa;
import com.ts.juridico.domain.model.UsuarioProcesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UsuarioContratoEmpresaJpaRepository extends JpaRepository<UsuarioContratoEmpresa, Long> {

    List<UsuarioContratoEmpresa> findByUserId_id(Long id);

    List<UsuarioContratoEmpresa> findByProcessoUuid(String processoUuid);

    UsuarioContratoEmpresa findByUserId(UsuarioProcesso user);
}
