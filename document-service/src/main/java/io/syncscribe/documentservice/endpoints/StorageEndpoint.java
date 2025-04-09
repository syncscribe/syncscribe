package io.syncscribe.documentservice.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.syncscribe.documentservice.components.documents.DocumentService;
import io.syncscribe.documentservice.components.storage.StorageService;
import io.syncscribe.documentservice.contracts.FileUploadResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/storages")
public class StorageEndpoint {
    private final DocumentService documentService;
    private final StorageService storageService;

    public StorageEndpoint(DocumentService documentService, StorageService storageService) {
        this.documentService = documentService;
        this.storageService = storageService;
    }

    @PostMapping(value = "/upload", params = { "docId" })
    public ResponseEntity<FileUploadResponse> upload(@RequestParam String docId,
            @RequestParam("file") MultipartFile file) {
        var doc = documentService.getDocument(docId);
        try {
            return ResponseEntity.ok(storageService.upload(doc, file));
        } catch (Exception e) {
            log.error("Failed to upload file", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
