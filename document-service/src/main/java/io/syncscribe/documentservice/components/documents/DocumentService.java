package io.syncscribe.documentservice.components.documents;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import io.syncscribe.common.auth.OAuthContext;
import io.syncscribe.common.utils.NanoIdGenerator;
import io.syncscribe.documentservice.contracts.CreateDocumentRequest;
import io.syncscribe.documentservice.datasource.models.Document;
import io.syncscribe.documentservice.datasource.models.DocumentLogRepository;
import io.syncscribe.documentservice.datasource.models.DocumentRepository;
import io.syncscribe.documentservice.datasource.models.ShareLinkRepository;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentLogRepository documentLogRepository;
    private final ShareLinkRepository shareLinkRepository;

    public DocumentService(DocumentRepository documentRepository, DocumentLogRepository documentLogRepository,
            ShareLinkRepository shareLinkRepository) {
        this.documentRepository = documentRepository;
        this.documentLogRepository = documentLogRepository;
        this.shareLinkRepository = shareLinkRepository;
    }

    public String createDocument(CreateDocumentRequest request) {
        var user = OAuthContext.getUser();
        var doc = new Document();
        doc.setId(NanoIdGenerator.generate());
        doc.setName(request.name());
        doc.setOwnerGuid(user.id());
        doc.setCreatedAt(OffsetDateTime.now());
        doc.setUpdatedAt(OffsetDateTime.now());
        documentRepository.save(doc);
        return doc.getId();
    }
}
