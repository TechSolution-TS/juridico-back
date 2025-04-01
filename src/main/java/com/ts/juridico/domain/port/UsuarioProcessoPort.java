package com.ts.juridico.domain.port;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioProcesso;

public interface UsuarioProcessoPort {

    UsuarioProcesso saveUserProcess(UsuarioProcessoCadastroDto usuarioProcessoCadastroDto);
    UsuarioProcesso findUser(String cpf);
}
