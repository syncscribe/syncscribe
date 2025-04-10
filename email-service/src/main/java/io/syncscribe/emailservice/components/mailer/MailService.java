package io.syncscribe.emailservice.components.mailer;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
public class MailService {
    private final MailProps mailProps;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public MailService(JavaMailSender mailSender, MailProps mailProps, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.mailProps = mailProps;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendShareLinkMail(ShareLinkMailRequest request) {
        var model = new EmailModel();
        
        model.setReceiver(request.recipients());
        model.setParams(request.params());
        model.setTemplate(request.subject());
        model.setSuccess(false);
        model.setCreatedby(request.createdBy());
        model.setCreatedAt(OffsetDateTime.now());

        var htmlContent = templateEngine.process("document-shared", context);
        var context = new Context();
        request.params.forEach(param -> {
            context.setVariable(param.getKey(), param.getValue());
        });
        request.recipients.forEach(recipient -> {
            context.setVariable("email", recipient);
            sendEmail("Someone shared a document with you", context, recipient);
        });
    }

    private void sendEmail(String subject, Context context, String recipient) {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);

        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(mailProps.getSender());

        mailSender.send(message);
    }
}
