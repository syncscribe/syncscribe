package io.syncscribe.fileservice.contracts;

import io.syncscribe.fileservice.datasource.models.ShareLinkRole;

import java.time.OffsetDateTime;
import java.util.List;

public record ShareDocumentRequest(
        ShareLinkRole role,
        String password,
        OffsetDateTime expiredAt,
        List<String> recipients) {
}
