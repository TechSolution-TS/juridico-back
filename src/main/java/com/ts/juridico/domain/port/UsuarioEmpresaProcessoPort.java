package com.ts.juridico.domain.port;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioEmpresaProcesso;
import com.ts.juridico.domain.model.UsuarioProcesso;

public interface UsuarioEmpresaProcessoPort {

    UsuarioEmpresaProcesso saveUserEnterprise(UsuarioProcessoCadastroDto usuarioProcessoCadastroDto);

    UsuarioEmpresaProcesso findEmpresaProcesso(Long userId);
    UsuarioEmpresaProcesso findEmpresaByProcessoUuid(String processoUuid);
    UsuarioEmpresaProcesso findEmpresaByUser(UsuarioProcesso user);
}
