package io.syncscribe.documentservice.components.documents;

import io.syncscribe.common.auth.OAuthContext;
import io.syncscribe.documentservice.components.security.EncryptionUtils;
import io.syncscribe.documentservice.components.storage.StorageService;
import io.syncscribe.documentservice.contracts.CreateDocumentRequest;
import io.syncscribe.documentservice.contracts.IllegalActionException;
import io.syncscribe.documentservice.contracts.ShareDocumentRequest;
import io.syncscribe.documentservice.contracts.WriteDocumentRequest;
import io.syncscribe.documentservice.datasource.models.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class DocumentService {
    private static final String DOCUMENT_NOT_FOUND_MSG = "Document not found";
    private final DocumentRepository documentRepository;
    private final DocumentLogRepository documentLogRepository;
    private final StorageService storageService;
    private final ShareLinkService shareLinkService;

    public DocumentService(
            DocumentRepository documentRepository,
            DocumentLogRepository documentLogRepository,
            StorageService storageService,
            ShareLinkService shareLinkService) {
        this.documentRepository = documentRepository;
        this.documentLogRepository = documentLogRepository;
        this.storageService = storageService;
        this.shareLinkService = shareLinkService;
    }

    public String createDocument(CreateDocumentRequest request) throws Exception {
        var doc = Document.newDocument(request.name());
        documentRepository.save(doc);
        storageService.createDocument(doc);
        return doc.getId();
    }

    @Transactional
    public String writeDocument(String id, WriteDocumentRequest request) throws Exception {
        var doc = documentRepository.findById(id).orElseThrow(() -> new RuntimeException(DOCUMENT_NOT_FOUND_MSG));
        var user = OAuthContext.getUser();
        if (!doc.getOwnerId().equals(user.id()) && !shareLinkService.hasPublicShareLink(doc.getId())) {
            throw new IllegalActionException("You are not allowed to modify this document");
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
        var doc = documentRepository.findById(id).orElseThrow(() -> new RuntimeException(DOCUMENT_NOT_FOUND_MSG));
        var userId = OAuthContext.getUser().id();
        if (!doc.getOwnerId().equals(userId)) {
            throw new IllegalActionException("You are not the owner of this document");
        }
        doc.setMarkForDelete(true);
        documentRepository.save(doc);
    }

    public Document getDocument(String id, String password) throws Exception {
        var doc = documentRepository.findById(id).orElseThrow(() -> new RuntimeException(DOCUMENT_NOT_FOUND_MSG));
        var user = OAuthContext.getUser();
        if (doc.getOwnerId().equals(user.id())) {
            return doc;
        }
        var shareLink = shareLinkService.getShareLinkByDocument(id).orElseThrow(() -> new RuntimeException("You are not allowed to access this document"));
        if ((shareLink.getExpiredAt() == null || shareLink.getExpiredAt().isAfter(OffsetDateTime.now())) && EncryptionUtils.decrypt(shareLink.getPassword()).equals(password)) {
            return doc;
        }
        throw new IllegalActionException("You are not allowed to access this document");
    }

    public List<Document> listDocuments(int page, int size) {
        var pageResult = documentRepository.findAll(PageRequest.of(page, size));
        return pageResult.getContent();
    }
}
