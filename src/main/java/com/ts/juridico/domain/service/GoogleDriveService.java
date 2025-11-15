package com.ts.juridico.domain.service;

import com.google.api.services.drive.model.File;
import com.ts.juridico.application.dto.response.ArquivoModeloPeticaoDto;
import com.ts.juridico.domain.model.ArquivoModeloPeticao;
import com.ts.juridico.domain.model.enums.StatusPeticao;
import com.ts.juridico.domain.port.ArquivoModeloPeticaoPort;
import com.ts.juridico.domain.port.DocumentosSindicatosPort;
import com.ts.juridico.domain.port.GooglePort;
import com.ts.juridico.infrastructure.persistence.mapper.ArquivoModeloPeticaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleDriveService {

    private final GooglePort googlePort;
    private final ArquivoModeloPeticaoPort arquivoModeloPeticaoPort;
    private final ArquivoModeloPeticaoMapper arquivoModeloPeticaoMapper;
    private final DocumentosSindicatosPort documentosSindicatosPort;

    @Transactional
    public String uploadFile(MultipartFile multipart, String typeFile) throws IOException {
        File file = googlePort.uploadFile(multipart);
        arquivoModeloPeticaoPort.saveModel(file, typeFile);
        return file.getId();
    }

    @Transactional
    public String uploadFileFolders(MultipartFile multipart, String uuidFolder) throws IOException {
        File file = googlePort.uploadFile(multipart);
        documentosSindicatosPort.saveFileFolder(file, uuidFolder);
        return file.getId();
    }

    public List<ArquivoModeloPeticaoDto> searchFiles(String typeFile) {
        List<ArquivoModeloPeticao> files = arquivoModeloPeticaoPort.listFiles(typeFile);
        return  arquivoModeloPeticaoMapper.tolistFundationDto(files);
    }

    public ArquivoModeloPeticao searchFileByFileId(String arquivoId) {
        return arquivoModeloPeticaoPort.findByArquivoId(arquivoId);
    }

    @Transactional
    public void deleteFileByFileId(String arquivoId) {
        arquivoModeloPeticaoPort.alteraTipo(arquivoId, StatusPeticao.DELETADO.getTipo(), StatusPeticao.DELETADO.getStatus());
    }

    @Transactional
    public void updateStatusFileByFileId(String arquivoId, String status) {
        if (status.equals(StatusPeticao.ANALISE.getStatus())) {
            arquivoModeloPeticaoPort.alteraTipo(arquivoId, StatusPeticao.ANALISE.getTipo(), StatusPeticao.ANALISE.getStatus());
        } else if (status.equals(StatusPeticao.REJEITADO.getStatus())) {
            arquivoModeloPeticaoPort.alteraTipo(arquivoId, StatusPeticao.REJEITADO.getTipo(), StatusPeticao.REJEITADO.getStatus());
        } else if (status.equals(StatusPeticao.CONCLUIDO.getStatus())) {
            arquivoModeloPeticaoPort.alteraTipo(arquivoId, StatusPeticao.CONCLUIDO.getTipo(), StatusPeticao.CONCLUIDO.getStatus());
        }
    }

    @Transactional
    public ArquivoModeloPeticao updateFile(ArquivoModeloPeticao arquivo) {
        return arquivoModeloPeticaoPort.update(arquivo);
    }

    public List<File> listFiles() throws IOException {
        return googlePort.listFiles();
    }

    public File getFileMetadata(String fileId) throws IOException {
        return googlePort.getFileMetadata(fileId);
    }

    public byte[] downloadFile(String fileId) throws IOException {
        return googlePort.downloadFile(fileId);
    }

    public List<ArquivoModeloPeticaoDto> searchFilesByAdvogado(String advogado) {
        List<ArquivoModeloPeticao> files = arquivoModeloPeticaoPort.listFilesByAdvogado(advogado);
        return  arquivoModeloPeticaoMapper.tolistFundationDto(files);
    }
}