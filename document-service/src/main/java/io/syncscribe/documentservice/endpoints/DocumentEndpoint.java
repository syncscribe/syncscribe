package io.syncscribe.documentservice.endpoints;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import io.syncscribe.common.contracts.ShareLinkMailRequest;
import io.syncscribe.documentservice.components.openfeign.EmailClient;
import io.syncscribe.documentservice.contracts.*;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/documents")
public class DocumentEndpoint {
    private final DocumentService documentService;
    private final EmailClient emailClient;

    public DocumentEndpoint(DocumentService documentService, EmailClient emailClient) {
        this.documentService = documentService;
        this.emailClient = emailClient;
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

    @PostMapping("/{id}/share")
    public void shareDocument(@PathVariable String id, ShareDocumentRequest request) {
        documentService.shareDocument(id, request);
        var mailRequest = new ShareLinkMailRequest(
                request.visitors().stream().map(DocumentVisitor::email).toList(),
                ImmutableMap.of()
        );
        emailClient.sendEmail(mailRequest);
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