package com.ts.juridico.domain.service;

import com.ts.juridico.domain.model.Usuario;
import com.ts.juridico.domain.port.UsuarioPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvogadoService {

    private final UsuarioPort usuarioPort;

    public Usuario findByUserLogin(String userName) {
        return usuarioPort.findByName(userName);
    }
}
