package com.ts.juridico.domain.port;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioEmpresaProcesso;

public interface UsuarioEmpresaProcessoPort {

    UsuarioEmpresaProcesso saveUserEnterprise(UsuarioProcessoCadastroDto usuarioProcessoCadastroDto);
}
