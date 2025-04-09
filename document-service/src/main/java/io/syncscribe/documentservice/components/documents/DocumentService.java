package io.syncscribe.documentservice.components.documents;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.syncscribe.common.auth.OAuthContext;
import io.syncscribe.documentservice.components.storage.StorageService;
import io.syncscribe.documentservice.contracts.CreateDocumentRequest;
import io.syncscribe.documentservice.contracts.IllegalActionException;
import io.syncscribe.documentservice.contracts.ShareDocumentRequest;
import io.syncscribe.documentservice.contracts.WriteDocumentRequest;
import io.syncscribe.documentservice.datasource.models.Document;
import io.syncscribe.documentservice.datasource.models.DocumentLogRepository;
import io.syncscribe.documentservice.datasource.models.DocumentRepository;
import io.syncscribe.documentservice.datasource.models.ShareLinkRepository;
import io.syncscribe.documentservice.datasource.models.ShareLinkRole;
import jakarta.transaction.Transactional;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentLogRepository documentLogRepository;
    private final ShareLinkRepository shareLinkRepository;
    private final StorageService storageService;

    public DocumentService(
            DocumentRepository documentRepository, DocumentLogRepository documentLogRepository,
            ShareLinkRepository shareLinkRepository, StorageService storageService) {
        this.documentRepository = documentRepository;
        this.documentLogRepository = documentLogRepository;
        this.shareLinkRepository = shareLinkRepository;
        this.storageService = storageService;
    }

    public String createDocument(CreateDocumentRequest request) throws Exception {
        var doc = Document.newDocument(request.name());
        documentRepository.save(doc);
        storageService.createDocument(doc);
        return doc.getId();
    }

    @Transactional
    public String writeDocument(String id, WriteDocumentRequest request) throws Exception {
        var doc = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
        var user = OAuthContext.getUser();
        if (!doc.getOwnerId().equals(user.id())) {
            var shareLink = shareLinkRepository.findByDocumentId(id)
                    .orElseThrow(() -> new IllegalActionException("You are not allowed to modify this document"));
            if (shareLink.getGeneralRole() == ShareLinkRole.READ
                    || !shareLink.getVisitors().stream().anyMatch(v -> v.email().equals(user.email()))) {
                throw new IllegalActionException("You are not allowed to modify this document");
            }
        }
        doc.setUpdatedAt(OffsetDateTime.now());
        doc.setUpdatedBy(OAuthContext.getUser().id());
        var url = storageService.updateDocument(doc, request.content());
        doc.setUrl(url);
        documentRepository.save(doc);

        var log = doc.createDocumentLog();
        var logUrl = storageService.logDocument(log, request.content());
        log.setUrl(logUrl);
        documentLogRepository.save(log);

        return url;
    }

    @Transactional
    public void softDeleteDocument(String id) {
        var doc = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
        var userId = OAuthContext.getUser().id();
        if (!doc.getOwnerId().equals(userId)) {
            throw new IllegalActionException("You are not the owner of this document");
        }
        doc.setMarkForDelete(true);
        documentRepository.save(doc);
    }

    public Document getDocument(String id) {
        var doc = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
        var user = OAuthContext.getUser();
        if (doc.getOwnerId().equals(user.id())) {
            return doc;
        }
        var shareLink = shareLinkRepository.findByDocumentId(id)
                .orElseThrow(() -> new RuntimeException("You are not allowed to access this document"));
        if (shareLink.getVisitors().stream().anyMatch(v -> v.email().equals(user.email()))) {
            return doc;
        }
        throw new RuntimeException("You are not allowed to access this document");
    }

    public List<Document> listDocuments(int page, int size) {
        var pageResult = documentRepository.findAll(PageRequest.of(page, size));
        return pageResult.getContent();
    }

    public void shareDocument(String docId, ShareDocumentRequest request) {
        var doc = documentRepository.findById(docId).orElseThrow(() -> new RuntimeException("Document not found"));
        var shareLink = shareLinkRepository.findByDocumentId(docId)
                .orElse(doc.createShareLink(request.visitors(), request.generalRole()));
        shareLink.setGeneralRole(request.generalRole());
        shareLink.setVisitors(request.visitors());
        shareLinkRepository.save(shareLink);
        //TODO send email
    }
}
