package com.ts.juridico.infrastructure.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleClient {

    private final Drive drive;
    private final ObjectMapper objectMapper;
    @Value("${driver.folders}")
    private String folders;

    public File uploadFile(MultipartFile multipart) throws IOException {
        List<String> parentFolders = objectMapper.readValue(folders, new TypeReference<List<String>>() {});

        if (parentFolders == null || parentFolders.isEmpty()) {
            throw new IllegalArgumentException("A lista de pastas não pode estar vazia.");
        }

        File fileMetadata = new File();
        fileMetadata.setName(multipart.getOriginalFilename());
        fileMetadata.setParents(List.of(parentFolders.get(0)));

        InputStreamContent content = new InputStreamContent(
                multipart.getContentType(),
                multipart.getInputStream()
        );

        File uploaded = drive.files()
                .create(fileMetadata, content)
                .setFields("id, name, mimeType, webViewLink, webContentLink")
                .execute();

        Permission anyoneCanRead = new Permission()
                .setType("anyone")
                .setRole("reader");
        drive.permissions().create(uploaded.getId(), anyoneCanRead).execute();

        for (int i = 1; i < parentFolders.size(); i++) {
            String targetFolderId = parentFolders.get(i);

            File copyMetadata = new File();
            copyMetadata.setName(uploaded.getName());
            copyMetadata.setParents(List.of(targetFolderId));

            drive.files()
                    .copy(uploaded.getId(), copyMetadata)
                    .setFields("id, name")
                    .execute();
        }

        return uploaded;
    }

    public List<File> listFiles() throws IOException {
        FileList result = drive.files().list()
                .setPageSize(100)
                .setFields("nextPageToken, files(id, name, webViewLink)")
                .execute();
        return result.getFiles() != null
                ? result.getFiles()
                : Collections.emptyList();
    }

    public File getFileMetadata(String fileId) throws IOException {
        return drive.files()
                .get(fileId)
                .setFields("id, name, mimeType, webViewLink, webContentLink")
                .execute();
    }

    public byte[] downloadFile(String fileId) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        drive.files()
                .get(fileId)
                .executeMediaAndDownloadTo(out);
        return out.toByteArray();
    }
}
