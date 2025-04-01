package com.ts.juridico.infrastructure.persistence.jpa;

import com.ts.juridico.domain.model.UsuarioProcesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioProcesssoJpaRepository extends JpaRepository<UsuarioProcesso, Long> {

    List<UsuarioProcesso> findByCpf(String cpf);
}
