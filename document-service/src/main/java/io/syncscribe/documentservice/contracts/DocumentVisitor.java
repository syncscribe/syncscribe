package io.syncscribe.documentservice.contracts;

import io.syncscribe.documentservice.datasource.models.ShareLinkRole;

public record DocumentVisitor(String email, ShareLinkRole role) {
    
}