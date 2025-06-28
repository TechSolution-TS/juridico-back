package com.ts.juridico.infrastructure.persistence.adapter;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioDocumento;
import com.ts.juridico.domain.model.UsuarioProcesso;
import com.ts.juridico.domain.port.UsuarioProcessoPort;
import com.ts.juridico.infrastructure.exception.UserNotFoundException;
import com.ts.juridico.infrastructure.persistence.jpa.UsuarioDocumentoJpaRepository;
import com.ts.juridico.infrastructure.persistence.jpa.UsuarioProcesssoJpaRepository;
import com.ts.juridico.infrastructure.persistence.mapper.UsuarioProcessoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsuarioProcessoAdapter implements UsuarioProcessoPort {

    private final UsuarioProcesssoJpaRepository usuarioProcesssoJpaRepository;
    private final UsuarioDocumentoJpaRepository usuarioDocumentoJpaRepository;
    private final UsuarioProcessoMapper mapper;

    @Override
    public UsuarioProcesso saveUserProcess(UsuarioProcessoCadastroDto usuarioProcessoCadastroDto) {
        UsuarioProcesso user = mapper.dtoToModel(usuarioProcessoCadastroDto);
        return usuarioProcesssoJpaRepository.save(user);
    }

    @Override
    public UsuarioProcesso findUser(String cpf) {
        return usuarioProcesssoJpaRepository.findByCpf(cpf)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Usuario with CPF " + cpf + " not found"));
    }

    @Override
    public UsuarioProcesso findUserByProcessoUuid(String processoUuid) {
        return usuarioProcesssoJpaRepository.findByProcessoUuid(processoUuid)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Usuario with Processo " + processoUuid + " not found"));
    }

    @Override
    public UsuarioProcesso findById(Long id) {
        return usuarioProcesssoJpaRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario with ID " + id + " not found"));
    }

    @Override
    public void saveDocumentProcess(Long id, String fileId, String processUuid) {
        usuarioDocumentoJpaRepository.save(mapper.dataToUsuarioDocumentoModel(id, fileId, processUuid));
    }

    @Override
    public UsuarioDocumento findByFileId(String fileId) {
        return usuarioDocumentoJpaRepository.findByFileId(fileId).orElseThrow(
                () -> new UserNotFoundException("Usuário não possui um documento associado com o id " + fileId)
        );
    }

    @Override
    public List<UsuarioDocumento> findByProcessUuid(String processUuid) {
        return usuarioDocumentoJpaRepository.findByProcessUuid(processUuid);
    }

    @Override
    public List<UsuarioDocumento> findByUserIdAndProcessUuidNull(Long userId) {
        return usuarioDocumentoJpaRepository.findAllByUserIdAndProcessUuidNull(userId);
    }

    @Override
    public void updateDocumentProcess(UsuarioDocumento document) {
        usuarioDocumentoJpaRepository.save(document);
    }
}
