package com.droute.driverservice.controller;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.droute.driverservice.dto.ImageUploadResponseDto;
import com.droute.driverservice.service.DriverEntityService;
import com.droute.driverservice.service.ImageUploadService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/file")
public class ImageController {

    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private DriverEntityService driverEntityService;

    @PostMapping("/uploadToGoogleDrive")
    public ResponseEntity<ImageUploadResponseDto> handleFileUpload(
            @RequestParam("image") MultipartFile file, 
            @RequestParam("userId") Long userId, 
            @RequestParam("documentName") String documentName) throws IOException, GeneralSecurityException {
        
        if (file.isEmpty()) {
            return new ResponseEntity<>(new ImageUploadResponseDto(400, "File is empty", null), HttpStatus.BAD_REQUEST);
        }
        
        var existingUser = driverEntityService.getUserById(userId);
		if(existingUser.getStatusCode() != HttpStatus.OK || existingUser.getBody().getEntity()==null) {
			throw new EntityNotFoundException("User Not exists with given Id");
		}

        // Validate file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || (!originalFilename.endsWith(".png") && 
                                         !originalFilename.endsWith(".jpeg") && 
                                         !originalFilename.endsWith(".jpg"))) {
            return new ResponseEntity<>(new ImageUploadResponseDto(400, "Invalid file format. Only JPG, JPEG, PNG allowed.", null), HttpStatus.BAD_REQUEST);
        }

        // Extract file extension
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Create a custom file name using userId & documentName
        String customFileName = userId + "_" + documentName.replaceAll("\\s+", "_") + fileExtension;

        // Create a temporary file with correct extension
        File tempFile = File.createTempFile("temp", fileExtension);
        file.transferTo(tempFile);

        // Upload to Google Drive with the custom name
        ImageUploadResponseDto res = imageUploadService.uploadImageToDrive(tempFile, customFileName);

        System.out.println(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
