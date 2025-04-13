package io.syncscribe.fileservice.endpoints;

import com.google.common.collect.ImmutableMap;
import io.syncscribe.common.contracts.ShareLinkMailRequest;
import io.syncscribe.fileservice.components.files.ShareLinkService;
import io.syncscribe.fileservice.components.remote.EmailClient;
import io.syncscribe.fileservice.contracts.ShareDocumentRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/share")
public class ShareLinkEndpoint {
    private final ShareLinkService shareLinkService;
    private final EmailClient emailClient;

    public ShareLinkEndpoint(ShareLinkService shareLinkService, EmailClient emailClient) {
        this.shareLinkService = shareLinkService;
        this.emailClient = emailClient;
    }

    @PostMapping("/{docId}/")
    public String createShareLink(@PathVariable String docId) {
        return shareLinkService.share(docId);
    }

    @PutMapping("/{id}")
    public void updateShareLink(@PathVariable String id, ShareDocumentRequest request) {
        shareLinkService.update(id, request);
        if (request.recipients() != null && !request.recipients().isEmpty()) {
            var mailRequest = new ShareLinkMailRequest(
                    request.recipients(), ImmutableMap.of()
            );
            emailClient.sendShareLinkEmail(mailRequest);
        }
    }
}
