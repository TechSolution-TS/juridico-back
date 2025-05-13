package com.ts.juridico.domain.port;

import com.ts.juridico.application.dto.request.UsuarioProcessoCadastroDto;
import com.ts.juridico.domain.model.UsuarioDocumento;
import com.ts.juridico.domain.model.UsuarioProcesso;

import java.util.List;

public interface UsuarioProcessoPort {

    UsuarioProcesso saveUserProcess(UsuarioProcessoCadastroDto usuarioProcessoCadastroDto);
    UsuarioProcesso findUser(String cpf);
    void saveDocumentProcess(Long id, String fileId, String processUuid);
    UsuarioDocumento findByFileId(String fileId);
    UsuarioProcesso findById(Long id);
    void updateDocumentProcess(UsuarioDocumento document);
    List<UsuarioDocumento> findByProcessUuid(String processUuid);
}
