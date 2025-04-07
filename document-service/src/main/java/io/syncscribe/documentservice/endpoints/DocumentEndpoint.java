package io.syncscribe.documentservice.endpoints;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.syncscribe.documentservice.components.documents.DocumentService;
import io.syncscribe.documentservice.contracts.CreateDocumentRequest;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentEndpoint {

    private final DocumentService documentService;

    public DocumentEndpoint(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public String createDocument(CreateDocumentRequest request) {
        return documentService.createDocument(request);
    }

    public void updateDocument() {
    }

    public void deleteDocument() {
    }

    public void getDocument() {
    }

    public void listDocuments() {
    }

    public void shareDocument() {
    }

    public void unshareDocument() {
    }
}