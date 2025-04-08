package io.syncscribe.documentservice.endpoints;

import java.util.List;
import java.util.stream.Collectors;

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
import io.syncscribe.documentservice.contracts.UnShareDocumentRequest;
import io.syncscribe.documentservice.contracts.WriteDocumentRequest;

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
                .map(doc -> new GetDocumentResponse(doc.getId(), doc.getName(), doc.getContent()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String createDocument(CreateDocumentRequest request) {
        request.validate();
        return documentService.createDocument(request);
    }

    @PostMapping("/{id}/share")
    public void shareDocument(@PathVariable String id, ShareDocumentRequest request) {
        documentService.shareDocument(id, request);
    }   

    @PutMapping("/{id}")
    public void write(@PathVariable String id, WriteDocumentRequest request) {
        documentService.writeDocument(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable String id) {
        documentService.deleteDocument(id);
    }

    @GetMapping("/{id}")
    public GetDocumentResponse getDocument(@PathVariable String id) {
        var doc = documentService.getDocument(id);
        return new GetDocumentResponse(doc.getId(), doc.getName(), doc.getContent());
    }

}