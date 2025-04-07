package io.syncscribe.documentservice.contracts;

import java.util.List;

public record UnShareDocumentRequest(List<String> emails) {
    public void validate() {
        if (emails == null || emails.isEmpty()) {
            throw new IllegalArgumentException("Emails cannot be empty");
        }
    }
}
