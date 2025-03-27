package com.ts.juridico.domain.service;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.port.InfoProcessoUsuarioPort;
import com.ts.juridico.domain.port.UsuarioContratoEmpresaPort;
import com.ts.juridico.domain.port.UsuarioEmpresaProcessoPort;
import com.ts.juridico.domain.port.UsuarioProcessoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioProcessoPort usuarioProcessoPort;
    private final UsuarioEmpresaProcessoPort usuarioEmpresaProcessoPort;
    private final UsuarioContratoEmpresaPort usuarioContratoEmpresaPort;
    private final InfoProcessoUsuarioPort infoProcessoUsuarioPort;

    @Transactional
    public void userRegister(UsuarioProcessoCadastroDto usuarioDto) {
        usuarioProcessoPort.saveUserProcess(usuarioDto);
        usuarioEmpresaProcessoPort.saveUserEnterprise(usuarioDto);
        usuarioContratoEmpresaPort.saveContractEnterprise(usuarioDto);
        infoProcessoUsuarioPort.saveInfoProcessUser(usuarioDto);
    }
}
