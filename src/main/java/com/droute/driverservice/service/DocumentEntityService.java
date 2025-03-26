package com.droute.driverservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.droute.driverservice.entity.DocumentEntity;
import com.droute.driverservice.entity.DriverEntity;
import com.droute.driverservice.exception.EntityAlreadyExistsException;
import com.droute.driverservice.repository.DocumentEntityRepository;
import com.droute.driverservice.repository.DriverEntityRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DocumentEntityService {
    @Autowired
    private DocumentEntityRepository documentEntityRepository;

    @Autowired
    private DriverEntityRepository driverEntityRepository;
    
    public DriverEntity getDriverById(Long driverId) {
        return driverEntityRepository.findById(driverId).orElseThrow(
            ()-> new EntityNotFoundException("Driver not found with given id = "+driverId));
    }
    public void deleteDocumentById(Long documentId) {
        getDocumentById(documentId);
        documentEntityRepository.deleteById(documentId);
    }

    public void deleteDocumentByDriverId(Long driverId) {
        getDriverById(driverId);
        documentEntityRepository.deleteByDriver_DriverId(driverId);
    }
    public DocumentEntity getDocumentById(Long documentId) {
        return documentEntityRepository.findById(documentId).orElseThrow(
            ()-> new EntityNotFoundException("Document not found with given id = "+documentId));
    }
    public Set<DocumentEntity> getDocumentByDriverId(Long driverId) {
        getDriverById(driverId);
        return documentEntityRepository.findByDriver_DriverId(driverId);
    }
    public DriverEntity postDocument(DocumentEntity document, Long driverId, String documentName) throws EntityAlreadyExistsException {
        // getDocumentById(documentId);
       var driver = getDriverById(driverId);
         var documents = driver.getDocuments();
            if (documents == null) {
            documents = new HashSet<>();
        }
        
        boolean documentExists = documents.stream()
            .anyMatch(doc -> doc.getDocumentName().equals(documentName));
        
        if (!documentExists) {
            documents.add(document);
            driver.setDocuments(documents);
            return driverEntityRepository.save(driver);
        } else {
            throw new EntityAlreadyExistsException("Document with the same name already exists.");
        }

    }
    public DocumentEntity updateDocumentById(DocumentEntity document) {
        return documentEntityRepository.save(document);
    }
    public Set<DocumentEntity> getAllDocumentsByDriverId(long driverId) {
        var driver = getDriverById(driverId);
        return driver.getDocuments();
    }
    public Set<DocumentEntity> getDocumentByDriverIdAndDocumentName(long driverId, String documentName) {
        var driver = getDriverById(driverId);
        var documents = driver.getDocuments();
        documents = documents.stream()
            .filter(doc -> doc.getDocumentName().contains(documentName)).collect(Collectors.toSet());
         
        if (documents.isEmpty()) {
            throw new EntityNotFoundException("Document not found with given name = " + documentName);
            
        }
        return documents;
    }

}
