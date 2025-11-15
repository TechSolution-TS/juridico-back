package com.ts.juridico.domain.port;

import com.google.api.services.drive.model.File;
import com.ts.juridico.application.dto.request.DocumentosSindicatosRequestDto;
import com.ts.juridico.domain.model.ArquivosDocumentosSindicatos;
import com.ts.juridico.domain.model.DocumentosSindicatos;

import java.util.List;

public interface DocumentosSindicatosPort {

    DocumentosSindicatos save(DocumentosSindicatosRequestDto dto);
    List<DocumentosSindicatos> findAll();
    ArquivosDocumentosSindicatos saveFileFolder(File file, String uuidFolder);
    List<ArquivosDocumentosSindicatos> findByDocumentoSindicatoUuid(String documentoSindicatoUuid);
    void deleteFolderByUuid(String uuid);
    void deleteFileByUuid(String fileUuid);
    void deleteFileByDocumentoSindicatoUuid(String documentoSindicatoUuid);
}
