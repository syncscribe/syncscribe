package io.syncscribe.fileservice.components.files;

import io.syncscribe.fileservice.contracts.ShareDocumentRequest;
import io.syncscribe.fileservice.datasource.models.FileRepository;
import io.syncscribe.fileservice.datasource.models.ShareLink;
import io.syncscribe.fileservice.datasource.models.ShareLinkRepository;
import io.syncscribe.fileservice.datasource.models.ShareLinkRole;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShareLinkService {
    private static final String DOCUMENT_NOT_FOUND_MSG = "Document not found";
    private static final String SHARE_LINK_NOT_FOUND_MSG = "Share link not found";
    private final FileRepository fileRepository;
    private final ShareLinkRepository shareLinkRepository;

    public ShareLinkService(FileRepository fileRepository, ShareLinkRepository shareLinkRepository) {
        this.fileRepository = fileRepository;
        this.shareLinkRepository = shareLinkRepository;
    }

    public Optional<ShareLink> getShareLinkByDocument(String docId) {
        return shareLinkRepository.findByFileId(docId);
    }

    public boolean hasPublicShareLink(String docId) {
        var shareLink = shareLinkRepository.findByFileId(docId).orElse(null);
        return shareLink != null && shareLink.getRole() == ShareLinkRole.WRITE;
    }

    public String share(String docId) {
        var doc = fileRepository.findById(docId).orElseThrow(() -> new RuntimeException(DOCUMENT_NOT_FOUND_MSG));
        var shareLink = shareLinkRepository.findByFileId(docId)
                .orElse(doc.createShareLink());
        shareLinkRepository.save(shareLink);
        return shareLink.getId();
    }

    public void update(String id, ShareDocumentRequest request) {
        var link = shareLinkRepository.findById(id).orElseThrow(() -> new RuntimeException(SHARE_LINK_NOT_FOUND_MSG));
        link.setRole(request.role());
        link.setPassword(request.password());
        link.setExpiredAt(request.expiredAt());
        shareLinkRepository.save(link);
    }
}
