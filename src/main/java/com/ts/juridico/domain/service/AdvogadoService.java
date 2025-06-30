package com.ts.juridico.domain.service;

import com.ts.juridico.domain.model.Usuario;
import com.ts.juridico.domain.port.UsuarioPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvogadoService {

    private final UsuarioPort usuarioPort;

    public Usuario findByUserLogin(String userName) {
        return usuarioPort.findByLogin(userName);
    }

    public void save(Usuario advogado) {
        usuarioPort.save(advogado);
    }

    public List<Usuario> findAll() {
        return usuarioPort.findAll();
    }
}
