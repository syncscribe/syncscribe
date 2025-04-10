package io.syncscribe.emailservice.endpoints;

import io.syncscribe.common.contracts.ShareLinkMailRequest;
import io.syncscribe.emailservice.components.mailer.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emails")
public class EmailEndpoint {
    private final MailService mailService;

    public EmailEndpoint(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/sendShareLink")
    public void sendShareLinkEmail(ShareLinkMailRequest request) {
        request.validate();
        mailService.sendShareLinkMail(request);
    }
}
