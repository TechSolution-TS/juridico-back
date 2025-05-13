package com.ts.juridico.domain.port;

import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GooglePort {

    File uploadFile(MultipartFile multipart) throws IOException;
    List<File> listFiles() throws IOException;
    File getFileMetadata(String fileId) throws IOException;
    byte[] downloadFile(String fileId) throws IOException;
}
