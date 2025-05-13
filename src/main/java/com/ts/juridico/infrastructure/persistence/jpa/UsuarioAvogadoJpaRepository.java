package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioAvogadoJpaRepository extends JpaRepository<Usuario, Long> {

    Usuario getReferenceByUuid(String uuid);
    Optional<Usuario> findByUuid(String uuid);

    Optional<Usuario> findByName(String email);
}
