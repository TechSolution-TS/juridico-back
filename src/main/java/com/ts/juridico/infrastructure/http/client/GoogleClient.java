package com.ts.juridico.infrastructure.http.client;

import com.google.api.client.http.FileContent;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import lombok.RequiredArgsConstructor;
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

    public File uploadFile(MultipartFile multipart) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(multipart.getOriginalFilename());
        fileMetadata.setParents(List.of("18VRN3ya1K3RBclBSURPH8HafY3eYM2Og"));

        // usa InputStreamContent para não precisar criar tmp
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
