package com.ts.juridico.infrastructure.http.adapters;

import com.google.api.services.drive.model.File;
import com.ts.juridico.domain.port.GooglePort;
import com.ts.juridico.infrastructure.http.client.GoogleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GoogleAdapter implements GooglePort {

    private final GoogleClient googleClient;

    @Override
    public File uploadFile(MultipartFile multipart) throws IOException {
        return googleClient.uploadFile(multipart);
    }

    @Override
    public List<File> listFiles() throws IOException {
        return googleClient.listFiles();
    }

    @Override
    public File getFileMetadata(String fileId) throws IOException {
        return googleClient.getFileMetadata(fileId);
    }

    @Override
    public byte[] downloadFile(String fileId) throws IOException {
        return googleClient.downloadFile(fileId);
    }
}
