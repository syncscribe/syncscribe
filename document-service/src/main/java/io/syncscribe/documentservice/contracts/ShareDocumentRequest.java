package io.syncscribe.documentservice.contracts;

import java.util.List;

import io.syncscribe.documentservice.datasource.models.ShareLinkRole;

public record ShareDocumentRequest(List<DocumentVisitor> visitors, ShareLinkRole generalRole) {
}
