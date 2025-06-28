package com.ts.juridico.domain.service;

import com.google.api.services.drive.model.File;
import com.ts.juridico.application.dto.response.ArquivoModeloPeticaoDto;
import com.ts.juridico.domain.model.ArquivoModeloPeticao;
import com.ts.juridico.domain.port.ArquivoModeloPeticaoPort;
import com.ts.juridico.domain.port.GooglePort;
import com.ts.juridico.infrastructure.persistence.mapper.ArquivoModeloPeticaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleDriveService {

    private final GooglePort googlePort;
    private final ArquivoModeloPeticaoPort arquivoModeloPeticaoPort;
    private final ArquivoModeloPeticaoMapper arquivoModeloPeticaoMapper;

    public String uploadFile(MultipartFile multipart, String typeFile) throws IOException {
        File file = googlePort.uploadFile(multipart);
        arquivoModeloPeticaoPort.saveModel(file, typeFile);
        return file.getId();
    }

    public List<ArquivoModeloPeticaoDto> searchFiles(String typeFile) {
        List<ArquivoModeloPeticao> files = arquivoModeloPeticaoPort.listFiles(typeFile);
        return  arquivoModeloPeticaoMapper.tolistFundationDto(files);
    }

    public ArquivoModeloPeticao searchFileByFileId(String arquivoId) {
        return arquivoModeloPeticaoPort.findByArquivoId(arquivoId);
    }

    public void deleteFileByFileId(String arquivoId) {
        arquivoModeloPeticaoPort.alteraTipo(arquivoId, "deletado");
    }

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
}