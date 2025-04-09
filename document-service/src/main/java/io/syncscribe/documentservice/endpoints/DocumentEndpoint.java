package io.syncscribe.documentservice.endpoints;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.syncscribe.documentservice.components.documents.DocumentService;
import io.syncscribe.documentservice.contracts.CreateDocumentRequest;
import io.syncscribe.documentservice.contracts.GetDocumentResponse;
import io.syncscribe.documentservice.contracts.ShareDocumentRequest;
import io.syncscribe.documentservice.contracts.WriteDocumentRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/documents")
public class DocumentEndpoint {

    private final DocumentService documentService;

    public DocumentEndpoint(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping(params = { "page", "size" })
    public List<GetDocumentResponse> listDocuments(@RequestParam int page, @RequestParam int size) {
        return documentService.listDocuments(page, size).stream()
                .map(doc -> new GetDocumentResponse(doc.getId(), doc.getName()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<String> createDocument(CreateDocumentRequest request) {
        request.validate();
        try {
            return ResponseEntity.ok(documentService.createDocument(request));
        } catch (Exception e) {
            log.error("Failed to create document", e);
            return ResponseEntity.internalServerError().body("Error creating document");
        }
    }

    @PostMapping("/{id}/share")
    public void shareDocument(@PathVariable String id, ShareDocumentRequest request) {
        documentService.shareDocument(id, request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> write(@PathVariable String id, WriteDocumentRequest request) {
        try {
            var url = documentService.writeDocument(id, request);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            log.error("Failed to write document", e);
            return ResponseEntity.internalServerError().body("Error writing document");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable String id) {
        documentService.softDeleteDocument(id);
    }

    @GetMapping("/{id}")
    public GetDocumentResponse getDocument(@PathVariable String id) {
        var doc = documentService.getDocument(id);
        return new GetDocumentResponse(doc.getId(), doc.getName());
    }

}