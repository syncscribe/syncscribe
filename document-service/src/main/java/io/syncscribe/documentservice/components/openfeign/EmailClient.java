package io.syncscribe.documentservice.components.openfeign;

import io.syncscribe.common.contracts.ShareLinkMailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "emailClient", configuration = FeignConfig.class)
public interface EmailClient {
    @PostMapping("/api/v1/emails/sendShareLink")
    void sendShareLinkEmail(ShareLinkMailRequest request);
}
