package com.ts.juridico.domain.service;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.application.dto.response.UsuarioProcessoCadastroResponseDto;
import com.ts.juridico.application.mapper.UsuarioProcessoCadastroMapper;
import com.ts.juridico.domain.model.*;
import com.ts.juridico.domain.port.InfoProcessoUsuarioPort;
import com.ts.juridico.domain.port.UsuarioContratoEmpresaPort;
import com.ts.juridico.domain.port.UsuarioEmpresaProcessoPort;
import com.ts.juridico.domain.port.UsuarioProcessoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioProcessoPort usuarioProcessoPort;
    private final UsuarioEmpresaProcessoPort usuarioEmpresaProcessoPort;
    private final UsuarioContratoEmpresaPort usuarioContratoEmpresaPort;
    private final InfoProcessoUsuarioPort infoProcessoUsuarioPort;
    private final UsuarioProcessoCadastroMapper usuarioProcessoCadastroMapper;
    private final ProcessoService processoService;

    @Transactional
    public UsuarioProcesso userRegister(UsuarioProcessoCadastroDto usuarioDto) {
        usuarioDto.setProcessoUuid(UUID.randomUUID().toString());

        UsuarioProcesso usuarioProcesso = usuarioProcessoPort.saveUserProcess(usuarioDto);
        usuarioDto.setUsuarioProcesso(usuarioProcesso);

        usuarioEmpresaProcessoPort.saveUserEnterprise(usuarioDto);
        usuarioContratoEmpresaPort.saveContractEnterprise(usuarioDto);
        infoProcessoUsuarioPort.saveInfoProcessUser(usuarioDto);

        return usuarioProcesso;
    }

    @Transactional
    public void updateUserRegister(UsuarioProcessoCadastroDto usuarioDto) {
        Processo processo = processoService.findByProcessUuid(usuarioDto.getProcessoUuid());
        InfoProcessoUsuario infoProcessoUsuario = infoProcessoUsuarioPort.findInfoProcessUserByProcessoUuid(usuarioDto.getProcessoUuid());

        if (StringUtils.hasText(usuarioDto.getTribunal())) {
            processo.setTribunal(usuarioDto.getTribunal());
            infoProcessoUsuario.setTribunal(usuarioDto.getTribunal());
        }

        if (StringUtils.hasText(usuarioDto.getNumeroProcesso())) {
            processo.setNumeroProcesso(usuarioDto.getNumeroProcesso());
        }

        if (StringUtils.hasText(usuarioDto.getStatus())) {
            processo.setStatus(usuarioDto.getStatus());
        }

        processoService.save(processo);
        infoProcessoUsuarioPort.save(infoProcessoUsuario);
    }

    public UsuarioProcessoCadastroDto findUserProcess(String cpf) {
        UsuarioProcesso user = usuarioProcessoPort.findUser(cpf);
        UsuarioEmpresaProcesso empresaProcesso = usuarioEmpresaProcessoPort.findEmpresaProcesso(user.getId());
        UsuarioContratoEmpresa usuarioContratoEmpresa = usuarioContratoEmpresaPort.findContractEnterprise(user.getId());
        InfoProcessoUsuario infoProcessoUsuario = infoProcessoUsuarioPort.findInfoProcessUser(user.getId());

        return usuarioProcessoCadastroMapper.modelToDto(user, empresaProcesso, usuarioContratoEmpresa, infoProcessoUsuario);
    }

    public UsuarioProcessoCadastroResponseDto findUserProcessByProcessUuid(String processUuid) {
        UsuarioProcesso user = usuarioProcessoPort.findUserByProcessoUuid(processUuid);
        UsuarioEmpresaProcesso empresaProcesso = usuarioEmpresaProcessoPort.findEmpresaByProcessoUuid(processUuid);
        UsuarioContratoEmpresa usuarioContratoEmpresa = usuarioContratoEmpresaPort.findContractEnterpriseByProcessUuid(processUuid);
        InfoProcessoUsuario infoProcessoUsuario = infoProcessoUsuarioPort.findInfoProcessUserByProcessoUuid(processUuid);
        Processo processo = processoService.findByProcessUuid(processUuid);

        return usuarioProcessoCadastroMapper.modelToResponseDto(user, empresaProcesso, usuarioContratoEmpresa, infoProcessoUsuario, processo);
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

    public List<UsuarioDocumento> searchDocumentByUserId(Long userId) {
        return usuarioProcessoPort.findByUserIdAndProcessUuidNull(userId);
    }

    public void updateDocumentProcessUser(UsuarioDocumento document) {
        usuarioProcessoPort.updateDocumentProcess(document);
    }
}
