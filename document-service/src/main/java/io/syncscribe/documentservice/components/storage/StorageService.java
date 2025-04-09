package io.syncscribe.documentservice.components.storage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.syncscribe.common.auth.OAuthContext;
import io.syncscribe.common.utils.NanoIdGenerator;
import io.syncscribe.documentservice.contracts.FileUploadResponse;
import io.syncscribe.documentservice.datasource.models.Document;
import io.syncscribe.documentservice.datasource.models.DocumentLog;
import io.syncscribe.documentservice.datasource.models.StorageObject;
import io.syncscribe.documentservice.datasource.models.StorageObjectRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageService {
    private final StorageObjectRepository storageObjectRepository;
    private final MinioService minioService;

    public StorageService(StorageObjectRepository storageObjectRepository, MinioService minioService) {
        this.storageObjectRepository = storageObjectRepository;
        this.minioService = minioService;
    }

    public void createDocument(Document doc) throws Exception {
        var user = OAuthContext.getUser();
        log.debug("Preparing storage for user {} and docId {}", user.id(), doc);
        var url = user.id() + "/" + doc.getId() + "/content.json";
        var response = minioService.createDir(url);
        if (response == null) {
            log.error("Failed to create storage");
            throw new RuntimeException("Failed to create storage");
        }
        var storageObject = new StorageObject();
        storageObject.setId(NanoIdGenerator.generate());
        storageObject.setName(doc.getName());
        storageObject.setDocumentId(doc.getId());
        storageObject.setUrl(url);
        storageObject.setMimeType("application/json");
        storageObject.setCreatedAt(OffsetDateTime.now());
        storageObjectRepository.save(storageObject);
    }

    public FileUploadResponse upload(Document doc, MultipartFile file) throws Exception {
        var user = OAuthContext.getUser();
        var object = new StorageObject();
        object.setId(NanoIdGenerator.generate());
        object.setName(file.getOriginalFilename());
        object.setDocumentId(doc.getId());
        object.setCreatedAt(OffsetDateTime.now());
        var contentType = detectMimeType(file);
        object.setMimeType(contentType);
        
        var url = user.id() + "/" + doc.getId() + "/" + object.getId();
        var response = minioService.uploadFile(file, url, contentType);
        if (response == null) {
            log.error("Failed to store file");
            throw new RuntimeException("Failed to store file");
        }
        object.setUrl(url);
        storageObjectRepository.save(object);
        return new FileUploadResponse(url);
    }

    public String updateDocument(Document doc, String content) throws Exception {
        var user = OAuthContext.getUser();
        var url = user.id() + "/" + doc.getId() + "/content.json";
        byte[] jsonBytes = content.getBytes(StandardCharsets.UTF_8);
        var response = minioService.uploadFile(url, new ByteArrayInputStream(jsonBytes));
        if (response == null) {
            log.error("Failed to update storage");
            throw new RuntimeException("Failed to update storage");
        }
        return url;
    }

    public String logDocument(DocumentLog documentLog, String content) throws Exception {
        var user = OAuthContext.getUser();
        var url = user.id() + "/" + documentLog.getDocument().getId() + "/log_" + documentLog.getId() + ".json";
        byte[] jsonBytes = content.getBytes(StandardCharsets.UTF_8);
        var response = minioService.uploadFile(url, new ByteArrayInputStream(jsonBytes));
        if (response == null) {
            log.error("Failed to log storage");
            throw new RuntimeException("Failed to log storage");
        }
        return url;
    }


    private String detectMimeType(MultipartFile file) throws IOException {
        var tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        String mimeType = Files.probeContentType(tempFile);
        Files.delete(tempFile);
        return mimeType;
    }
}
