package io.syncscribe.documentservice.endpoints;

import io.syncscribe.documentservice.components.documents.DocumentService;
import io.syncscribe.documentservice.contracts.CreateDocumentRequest;
import io.syncscribe.documentservice.contracts.GetDocumentResponse;
import io.syncscribe.documentservice.contracts.WriteDocumentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/documents")
public class DocumentEndpoint {
    private final DocumentService documentService;

    public DocumentEndpoint(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping(params = {"page", "size"})
    public List<GetDocumentResponse> listDocuments(@RequestParam int page, @RequestParam int size) {
        return documentService.listDocuments(page, size).stream()
                .map(doc -> new GetDocumentResponse(doc.getId(), doc.getName()))
                .toList();
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
    public ResponseEntity<GetDocumentResponse> getDocument(@PathVariable String id, @RequestParam String password) {
        try {
            var doc = documentService.getDocument(id, password);
            var response = new GetDocumentResponse(doc.getId(), doc.getName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to get document", e);
            return ResponseEntity.badRequest().build();
        }
    }

}