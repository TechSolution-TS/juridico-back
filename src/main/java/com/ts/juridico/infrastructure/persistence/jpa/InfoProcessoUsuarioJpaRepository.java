package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.InfoProcessoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InfoProcessoUsuarioJpaRepository extends JpaRepository<InfoProcessoUsuario, Long> {

    @Transactional
    List<InfoProcessoUsuario> findByUserId_id(Long id);
}
