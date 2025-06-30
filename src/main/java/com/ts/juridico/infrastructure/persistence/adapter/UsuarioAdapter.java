package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.domain.model.Usuario;
import com.ts.juridico.domain.port.UsuarioPort;
import com.ts.juridico.infrastructure.exception.UserNotFoundException;
import com.ts.juridico.infrastructure.persistence.jpa.UsuarioAvogadoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsuarioAdapter implements UsuarioPort {

    private final UsuarioAvogadoJpaRepository usuarioAvogadoJpaRepository;

    public Usuario findByLogin(String userName) {
        return usuarioAvogadoJpaRepository.findByLogin(userName).orElseThrow(
                () -> new UserNotFoundException("Invalid credentials"));
    }

    @Override
    public void save(Usuario advogado) {
        usuarioAvogadoJpaRepository.save(advogado);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioAvogadoJpaRepository.findAll();
    }
}

