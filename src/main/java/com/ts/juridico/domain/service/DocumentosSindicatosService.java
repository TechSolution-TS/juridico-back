package com.ts.juridico.domain.service;

import com.ts.juridico.application.dto.request.DocumentosSindicatosRequestDto;
import com.ts.juridico.application.dto.response.ArquivosDocumentosSindicatosReponseDto;
import com.ts.juridico.application.dto.response.DocumentosSindicatosResponseDto;
import com.ts.juridico.application.mapper.DocumentosSindicatosMapper;
import com.ts.juridico.domain.model.ArquivosDocumentosSindicatos;
import com.ts.juridico.domain.model.DocumentosSindicatos;
import com.ts.juridico.domain.port.DocumentosSindicatosPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentosSindicatosService {

    private final DocumentosSindicatosPort documentosSindicatosPort;
    private final DocumentosSindicatosMapper documentosSindicatosMapper;

    @Transactional
    public void createNewFolder(DocumentosSindicatosRequestDto dto) {
        documentosSindicatosPort.save(dto);
    }

    public List<DocumentosSindicatosResponseDto> searchFolders() {
        return documentosSindicatosMapper.toList(documentosSindicatosPort.findAll());
    }

    public List<ArquivosDocumentosSindicatosReponseDto> searchFiles(String documentoSindicatoUuid) {
        return documentosSindicatosMapper.toListFilesResponse(documentosSindicatosPort.findByDocumentoSindicatoUuid(documentoSindicatoUuid));
    }

    @Transactional
    public void deleteFolder(String uuid) {
        documentosSindicatosPort.deleteFileByDocumentoSindicatoUuid(uuid);
        documentosSindicatosPort.deleteFolderByUuid(uuid);
    }

    @Transactional
    public void deleteFile(String fileUuid) {
        documentosSindicatosPort.deleteFileByUuid(fileUuid);
    }
}
