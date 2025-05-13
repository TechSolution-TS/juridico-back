package com.ts.juridico.domain.port;

import com.google.api.services.drive.model.File;
import com.ts.juridico.domain.model.ArquivoModeloPeticao;

import java.util.List;

public interface ArquivoModeloPeticaoPort {

    void saveModel(File file, String typeFile);
    List<ArquivoModeloPeticao> listFiles(String typeFile);
    ArquivoModeloPeticao findByArquivoId(String arquivoId);
    ArquivoModeloPeticao update(ArquivoModeloPeticao arquivo);
}
