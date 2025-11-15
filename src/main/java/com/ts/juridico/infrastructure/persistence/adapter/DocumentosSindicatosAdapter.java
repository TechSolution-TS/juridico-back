package com.ts.juridico.infrastructure.persistence.adapter;

import com.google.api.services.drive.model.File;
import com.ts.juridico.application.dto.request.DocumentosSindicatosRequestDto;
import com.ts.juridico.application.mapper.DocumentosSindicatosMapper;
import com.ts.juridico.domain.model.ArquivosDocumentosSindicatos;
import com.ts.juridico.domain.model.DocumentosSindicatos;
import com.ts.juridico.domain.port.DocumentosSindicatosPort;
import com.ts.juridico.infrastructure.persistence.jpa.ArquivosDocumentosSindicatosJpaRepository;
import com.ts.juridico.infrastructure.persistence.jpa.DocumentosSindicatosJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DocumentosSindicatosAdapter implements DocumentosSindicatosPort {

    private final DocumentosSindicatosJpaRepository documentosSindicatosJpaRepository;
    private final ArquivosDocumentosSindicatosJpaRepository arquivosDocumentosSindicatosJpaRepository;
    private final DocumentosSindicatosMapper documentosSindicatosMapper;

    @Override
    public DocumentosSindicatos save(DocumentosSindicatosRequestDto dto) {
        return documentosSindicatosJpaRepository.save(documentosSindicatosMapper.toEntity(dto));
    }

    @Override
    public List<DocumentosSindicatos> findAll() {
        return documentosSindicatosJpaRepository.findAll();
    }

    @Override
    public ArquivosDocumentosSindicatos saveFileFolder(File file, String uuidFolder) {
        return arquivosDocumentosSindicatosJpaRepository.save(documentosSindicatosMapper.toEntityFile(file, uuidFolder));
    }

    @Override
    public List<ArquivosDocumentosSindicatos> findByDocumentoSindicatoUuid(String documentoSindicatoUuid) {
        return arquivosDocumentosSindicatosJpaRepository.findByDocumentoSindicatoUuid(documentoSindicatoUuid);
    }

    @Override
    public void deleteFolderByUuid(String uuid) {
        documentosSindicatosJpaRepository.deleteByUuid(uuid);
    }

    @Override
    public void deleteFileByUuid(String fileUuid) {
        arquivosDocumentosSindicatosJpaRepository.deleteByUuid(fileUuid);
    }

    @Override
    public void deleteFileByDocumentoSindicatoUuid(String documentoSindicatoUuid) {
        arquivosDocumentosSindicatosJpaRepository.deleteByDocumentoSindicatoUuid(documentoSindicatoUuid);
    }
}
