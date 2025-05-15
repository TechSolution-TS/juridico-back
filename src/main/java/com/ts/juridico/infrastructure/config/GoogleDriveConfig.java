package com.ts.juridico.infrastructure.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Configuration
public class GoogleDriveConfig {

    private static final String APPLICATION_NAME = "MeuApp";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${credential.google}")
    private String credentialsJson;

    @Bean
    public Drive googleDrive() throws Exception {
        try (InputStream in = new ByteArrayInputStream(
                credentialsJson.getBytes(StandardCharsets.UTF_8))) {

            GoogleCredential credential = GoogleCredential
                    .fromStream(in)
                    .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));

            return new Drive.Builder(new NetHttpTransport(), JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }
    }
}
