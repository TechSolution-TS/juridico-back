package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.UsuarioEmpresaProcesso;
import com.ts.juridico.domain.model.UsuarioProcesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UsuarioEmpresaProcessoJpaRepository extends JpaRepository<UsuarioEmpresaProcesso, Long> {

    List<UsuarioEmpresaProcesso> findByUserId_id(Long id);

    List<UsuarioEmpresaProcesso> findByProcessoUuid(String processoUuid);

    List<UsuarioEmpresaProcesso> findByUserId(UsuarioProcesso user);
}
