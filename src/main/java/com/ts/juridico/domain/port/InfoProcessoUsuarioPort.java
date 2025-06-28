package com.ts.juridico.domain.port;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.InfoProcessoUsuario;

public interface InfoProcessoUsuarioPort {

    InfoProcessoUsuario saveInfoProcessUser(UsuarioProcessoCadastroDto usuarioProcessoCadastroDto);
    InfoProcessoUsuario findInfoProcessUser(Long userId);
    InfoProcessoUsuario findInfoProcessUserByProcessoUuid(String processoUuid);
    InfoProcessoUsuario save(InfoProcessoUsuario infoProcessoUsuario);
}
