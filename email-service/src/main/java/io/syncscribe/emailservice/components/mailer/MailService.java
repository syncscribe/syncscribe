package io.syncscribe.emailservice.components.mailer;

import io.syncscribe.common.contracts.ShareLinkMailRequest;
import io.syncscribe.emailservice.datasource.models.EmailModel;
import io.syncscribe.emailservice.datasource.models.EmailRepository;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@Slf4j
@Service
public class MailService {
    private final MailProps mailProps;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final EmailRepository emailRepository;

    public MailService(JavaMailSender mailSender, MailProps mailProps, TemplateEngine templateEngine, EmailRepository emailRepository) {
        this.mailSender = mailSender;
        this.mailProps = mailProps;
        this.templateEngine = templateEngine;
        this.emailRepository = emailRepository;
    }

    @Async
    public void sendShareLinkMail(ShareLinkMailRequest request) {
        var context = new Context();
        var htmlContent = templateEngine.process("document-shared", context);
        request.params().forEach(context::setVariable);

        var emails = new ArrayList<EmailModel>();
        request.recipients().forEach(recipient -> {
            context.setVariable("email", recipient);
            var model = new EmailModel();
            model.setRecipient(recipient);
            model.setParams(request.params());
            model.setTemplate("document-shared");
            model.setCreatedAt(OffsetDateTime.now());
            try {
                sendEmail("Someone shared a document with you", htmlContent, recipient);
                model.setSuccess(true);
            } catch (MessagingException e) {
                log.error("Cannot send email to recipient: {} with error: {}", recipient, e.getMessage());
                model.setSuccess(false);
            }
            emails.add(model);
        });
        emailRepository.saveAll(emails);
    }

    private void sendEmail(String subject, String content, String recipient) throws MessagingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(mailProps.getSender());
        mailSender.send(message);
    }
}
