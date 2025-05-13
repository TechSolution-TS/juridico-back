package com.ts.juridico.domain.service;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.application.mapper.UsuarioProcessoCadastroMapper;
import com.ts.juridico.domain.model.*;
import com.ts.juridico.domain.port.InfoProcessoUsuarioPort;
import com.ts.juridico.domain.port.UsuarioContratoEmpresaPort;
import com.ts.juridico.domain.port.UsuarioEmpresaProcessoPort;
import com.ts.juridico.domain.port.UsuarioProcessoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public UsuarioProcesso findUserProcessById(Long id) {
        return usuarioProcessoPort.findById(id);
    }

    public void saveDocumentProcessUser(Long userId, String fileId, String processUuid) {
        usuarioProcessoPort.saveDocumentProcess(userId, fileId, processUuid);
    }

    public void addDocumentProcessUser(String cpf, String fileId) {
        UsuarioProcesso user = usuarioProcessoPort.findUser(cpf);
        usuarioProcessoPort.saveDocumentProcess(user.getId(), fileId, null);
    }

    public UsuarioDocumento searchDocumentById(String fileId) {
        return usuarioProcessoPort.findByFileId(fileId);
    }

    public List<UsuarioDocumento> searchDocumentByProcessUuid(String processUuid) {
        return usuarioProcessoPort.findByProcessUuid(processUuid);
    }

    public void updateDocumentProcessUser(UsuarioDocumento document) {
        usuarioProcessoPort.updateDocumentProcess(document);
    }
}
