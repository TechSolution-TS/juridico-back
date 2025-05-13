package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.domain.model.Usuario;
import com.ts.juridico.domain.port.UsuarioPort;
import com.ts.juridico.infrastructure.exception.UserNotFoundException;
import com.ts.juridico.infrastructure.persistence.jpa.UsuarioAvogadoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsuarioAdapter implements UsuarioPort {

    private final UsuarioAvogadoJpaRepository usuarioAvogadoJpaRepository;

    public Usuario findByName(String userName) {
        return usuarioAvogadoJpaRepository.findByName(userName).orElseThrow(
                () -> new UserNotFoundException("Invalid credentials"));
    }
}

