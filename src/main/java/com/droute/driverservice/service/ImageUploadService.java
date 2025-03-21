package com.droute.driverservice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.droute.driverservice.dto.response.ImageUploadResponseDto;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import org.springframework.stereotype.Service;

@Service
public class ImageUploadService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();
    private static final String FOLDER_ID = "1aA-GtfxmpnVsi9uij23MOYxfIjSjKw7h"; // Your Google Drive Folder ID

    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "cred.json");
        return filePath.toString();
    }

    public ImageUploadResponseDto uploadImageToDrive(File file, String customFileName) throws GeneralSecurityException, IOException {
        ImageUploadResponseDto res = new ImageUploadResponseDto();

        try {
            // ✅ Validate file type before processing
            String fileName = file.getName().toLowerCase();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")) {
                res.setStatus(400);
                res.setMessage("Only JPG, JPEG, and PNG files are allowed.");
                return res;
            }

            // ✅ Determine MIME type dynamically
            String mimeType = Files.probeContentType(file.toPath());
            if (mimeType == null) mimeType = "application/octet-stream"; // Fallback in case of null

            Drive drive = createDriveService();

            // ✅ Set custom file name
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(customFileName); // Assign custom file name
            fileMetaData.setParents(Collections.singletonList(FOLDER_ID));

            FileContent mediaContent = new FileContent(mimeType, file);
            com.google.api.services.drive.model.File uploadedFile = drive.files()
                    .create(fileMetaData, mediaContent)
                    .setFields("id")
                    .execute();

            String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);

            // ✅ Delete file after successful upload
            file.delete();

            res.setStatus(200);
            res.setMessage("Image Successfully Uploaded To Google Drive");
            res.setUrl(imageUrl);
        } catch (Exception e) {
            System.out.println("Upload Failed: " + e.getMessage());
            res.setStatus(500);
            res.setMessage("Upload failed: " + e.getMessage());
        }
        return res;
    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials))  // ✅ Corrected this
                .setApplicationName("dRoute-storage")  // ✅ Set application name
                .build();
    }
}
