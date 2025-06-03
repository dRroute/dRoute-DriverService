package com.droute.driverservice.controller;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.droute.driverservice.dto.response.CommonResponseDto;
import com.droute.driverservice.dto.response.ImageUploadResponseDto;
import com.droute.driverservice.dto.response.ResponseBuilder;
import com.droute.driverservice.entity.DocumentEntity;
import com.droute.driverservice.exception.EntityAlreadyExistsException;
import com.droute.driverservice.service.DocumentEntityService;
import com.droute.driverservice.service.DriverEntityService;
import com.droute.driverservice.service.ImageUploadService;


@RestController
@RequestMapping("/api/driver/document")
public class DocumentController {

    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private DriverEntityService driverEntityService;
    @Autowired
    private DocumentEntityService documentEntityService;


    //Api to upload document one at a time.
    @PostMapping(value="/uploadToGoogleDrive", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponseDto<String>> handleFileUpload(
            @RequestParam("file") MultipartFile file, 
            @RequestParam("driverId") Long driverId, 
            @RequestParam("documentName") String documentName) throws IOException, GeneralSecurityException, EntityAlreadyExistsException {
        
        if (file.isEmpty()) {
            return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, "File is empty", "DOC_400_EMPTY_FILE");
            // return new ResponseEntity<>(new ImageUploadResponseDto(400, "File is empty", null), HttpStatus.BAD_REQUEST);
        }
        
        var existingDriver = driverEntityService.findDriverByDriverId(driverId);
        
        // Validate file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || (!originalFilename.endsWith(".png") && 
                                         !originalFilename.endsWith(".jpeg") && 
                                         !originalFilename.endsWith(".jpg"))) {

           return  ResponseBuilder.failure(HttpStatus.BAD_REQUEST, "Invalid file format. Only JPG, JPEG, PNG allowed.", "DOC_400_INVALID_FILE_FORMAT");
            // return new ResponseEntity<>(new ImageUploadResponseDto(400, "Invalid file format. Only JPG, JPEG, PNG allowed.", null), HttpStatus.BAD_REQUEST);
        }

        // Extract file extension
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Create a custom file name using userId & documentName
        String customFileName = existingDriver.getDriverId() + "_" + documentName.replaceAll("\\s+", "-").toLowerCase() ;
        String customFileNameWithExtension = customFileName + fileExtension;

         // Create a temporary file with correct extension
        File tempFile = File.createTempFile("temp", fileExtension);
        file.transferTo(tempFile);

        // Check if a document with the same name already exists for the driver
        Set<DocumentEntity> existingDocuments = documentEntityService.getDocumentByDriverIdAndDocumentName(
                existingDriver.getDriverId(), customFileName, "check");
        System.out.println("Existing Documents: " + existingDocuments);
        if (!existingDocuments.isEmpty()) {
            var document = existingDocuments.iterator().next();
        
            System.out.println("Document already exists for this driver: " + existingDocuments);
            // If a document with the same name exists, update the file in Google Drive
            ImageUploadResponseDto res = imageUploadService.updateFileInDrive(tempFile, document.getDocumentUrl(), document.getDocumentName());
            if (res.getStatus() == 200) {
                // Delete the temporary file after successful upload
                tempFile.delete();
                document.setDocumentUrl(res.getUrl());
                documentEntityService.updateDocumentById(document);
                return ResponseBuilder.success(HttpStatus.CREATED, res.getMessage(), res.getUrl());
            } else if (res.getStatus() == 400) {
                return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, res.getMessage(), "DOC_400_INVALID_FILE");
            } else {
                return ResponseBuilder.failure(HttpStatus.INTERNAL_SERVER_ERROR, res.getMessage(), "DOC_500_UPLOAD_FAILED");
                
            }
            // return new ResponseEntity<>(new ImageUploadResponseDto(400, "Document with the same name already exists for this driver.", null), HttpStatus.BAD_REQUEST);
        }
       

        // Upload to Google Drive with the custom name
        ImageUploadResponseDto res = imageUploadService.uploadImageToDrive(tempFile, customFileNameWithExtension);
        if (res.getStatus() == 200) {
            // Delete the temporary file after successful upload
            tempFile.delete();
            var document = new DocumentEntity();
            document.setDocumentName(customFileName);
            document.setDocumentType(fileExtension);
            document.setDocumentUrl(res.getUrl());
            document.setDriver(existingDriver);
            documentEntityService.postDocument(document , existingDriver.getDriverId(), customFileName);
            return ResponseBuilder.success(HttpStatus.CREATED, res.getMessage(), res.getUrl());
          
         
            
        } else if(res.getStatus() == 400){
           return  ResponseBuilder.failure(HttpStatus.BAD_REQUEST, res.getMessage(), "DOC_400_INVALID_FILE");
            // return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else {
            return ResponseBuilder.failure(HttpStatus.INTERNAL_SERVER_ERROR, res.getMessage(), "DOC_500_UPLOAD_FAILED");
            // return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    // Get all documents by driverId
    @GetMapping("/getAllDocumentsByDriverId")
    public ResponseEntity<CommonResponseDto<Set<DocumentEntity>>> getAllDocumentsByDriverId(@RequestParam String driverId) {
        var documents = documentEntityService.getAllDocumentsByDriverId(Long.parseLong(driverId));
        return ResponseBuilder.success(HttpStatus.OK,"Documents fetched successfully.", documents);
  
    }

    // get document by documentId
    @GetMapping("/getDocumentById")
    public ResponseEntity<CommonResponseDto<DocumentEntity>> getDocumentById(@RequestParam String documentId) {
        var document = documentEntityService.getDocumentById(Long.parseLong(documentId));
        return ResponseBuilder.success(HttpStatus.OK,"Document fetched successfully.", document);
    }
    
    //Get Document By Name and Driver Id
    @GetMapping("/getDocumentByName")
    public ResponseEntity<CommonResponseDto<Set<DocumentEntity>>> getMethodName(@RequestParam String driverId, 
        @RequestParam String documentName) {
        var documents = documentEntityService.getDocumentByDriverIdAndDocumentName(Long.parseLong(driverId), documentName , "get");
        return ResponseBuilder.success(HttpStatus.OK,"Document fetched successfully.", documents);
    }

    //Delete Document By Id

    //Update Document By Id
    

}
