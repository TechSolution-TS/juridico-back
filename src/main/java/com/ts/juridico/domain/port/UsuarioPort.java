package com.ts.juridico.domain.port;

import com.ts.juridico.domain.model.Usuario;

import java.util.List;

public interface UsuarioPort {

    Usuario findByLogin(String userName);

    void save(Usuario advogado);

    List<Usuario> findAll();
}
