package io.syncscribe.documentservice.contracts;

import io.syncscribe.documentservice.datasource.models.ShareLinkRole;

import java.time.OffsetDateTime;
import java.util.List;

public record ShareDocumentRequest(
        ShareLinkRole role,
        String password,
        OffsetDateTime expiredAt,
        List<String> recipients) {
}
