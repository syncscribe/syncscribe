package io.syncscribe.documentservice.contracts;

public record CreateDocumentRequest(String name) {
    public void validate() {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
    }
}
