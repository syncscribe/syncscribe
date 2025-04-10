package io.syncscribe.common.contracts;

import java.util.List;
import java.util.Map;

public record ShareLinkMailRequest(List<String> recipients, Map<String, Object> params) {
    public void validate() {
        if (recipients == null || recipients.isEmpty()) {
            throw new IllegalArgumentException("Recipients are required");
        }
    }
}
