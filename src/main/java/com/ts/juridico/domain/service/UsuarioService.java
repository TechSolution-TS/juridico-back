package com.ts.juridico.domain.service;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.application.mapper.UsuarioProcessoCadastroMapper;
import com.ts.juridico.domain.model.InfoProcessoUsuario;
import com.ts.juridico.domain.model.UsuarioContratoEmpresa;
import com.ts.juridico.domain.model.UsuarioEmpresaProcesso;
import com.ts.juridico.domain.model.UsuarioProcesso;
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
    private final UsuarioProcessoCadastroMapper usuarioProcessoCadastroMapper;

    @Transactional
    public void userRegister(UsuarioProcessoCadastroDto usuarioDto) {
        usuarioProcessoPort.saveUserProcess(usuarioDto);
        usuarioEmpresaProcessoPort.saveUserEnterprise(usuarioDto);
        usuarioContratoEmpresaPort.saveContractEnterprise(usuarioDto);
        infoProcessoUsuarioPort.saveInfoProcessUser(usuarioDto);
    }

    public UsuarioProcessoCadastroDto findUserProcess(String cpf) {
        UsuarioProcesso user = usuarioProcessoPort.findUser(cpf);
        UsuarioEmpresaProcesso empresaProcesso = usuarioEmpresaProcessoPort.findEmpresaProcesso(user.getId());
        UsuarioContratoEmpresa usuarioContratoEmpresa = usuarioContratoEmpresaPort.findContractEnterprise(user.getId());
        InfoProcessoUsuario infoProcessoUsuario = infoProcessoUsuarioPort.findInfoProcessUser(user.getId());

        return usuarioProcessoCadastroMapper.modelToDto(user, empresaProcesso, usuarioContratoEmpresa, infoProcessoUsuario);
    }
}
