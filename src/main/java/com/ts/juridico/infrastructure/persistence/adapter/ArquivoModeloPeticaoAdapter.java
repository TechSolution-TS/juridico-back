package com.ts.juridico.infrastructure.persistence.adapter;

import com.google.api.services.drive.model.File;
import com.ts.juridico.domain.model.ArquivoModeloPeticao;
import com.ts.juridico.domain.port.ArquivoModeloPeticaoPort;
import com.ts.juridico.infrastructure.persistence.jpa.ArquivoModeloPeticaoJpaRepository;
import com.ts.juridico.infrastructure.persistence.mapper.ArquivoModeloPeticaoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArquivoModeloPeticaoAdapter implements ArquivoModeloPeticaoPort {

    private final ArquivoModeloPeticaoJpaRepository arquivoModeloPeticaoJpaRepository;
    private final ArquivoModeloPeticaoMapper arquivoModeloPeticaoMapper;

    public void saveModel(File file, String typeFile) {
        arquivoModeloPeticaoJpaRepository.save(arquivoModeloPeticaoMapper.fileToModel(file, typeFile));
    }

    @Override
    public List<ArquivoModeloPeticao> listFiles(String typeFile) {
        return arquivoModeloPeticaoJpaRepository.findByType(typeFile);
    }

    @Override
    public ArquivoModeloPeticao findByArquivoId(String arquivoId) {
        return arquivoModeloPeticaoJpaRepository.findByArquivoId(arquivoId);
    }

    @Override
    public ArquivoModeloPeticao update(ArquivoModeloPeticao arquivo) {
        return arquivoModeloPeticaoJpaRepository.save(arquivo);
    }

    @Override
    public void alteraTipo(String arquivoId, String novoTipo, String status) {
        int updated = arquivoModeloPeticaoJpaRepository.updateTypeByArquivoId(arquivoId, novoTipo, status);
        if (updated == 0) {
            throw new EntityNotFoundException("Nenhum registro com arquivoId=" + arquivoId);
        }
    }

    @Override
    public List<ArquivoModeloPeticao> listFilesByAdvogado(String advogado) {
        return arquivoModeloPeticaoJpaRepository.findByAdvogado(advogado);
    }
}
