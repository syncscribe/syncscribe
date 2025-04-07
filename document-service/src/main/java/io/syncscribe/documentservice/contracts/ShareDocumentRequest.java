package io.syncscribe.documentservice.contracts;

import java.util.List;

public record ShareDocumentRequest(List<String> emails, Boolean isPublic) {
    public void validate() {
        if (emails == null || emails.isEmpty()) {
            throw new IllegalArgumentException("Emails cannot be empty");
        }
    }
}
