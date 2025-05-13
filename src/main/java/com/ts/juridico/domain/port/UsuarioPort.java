package com.ts.juridico.domain.port;

import com.ts.juridico.domain.model.Usuario;

public interface UsuarioPort {

    Usuario findByName(String userName);
}
