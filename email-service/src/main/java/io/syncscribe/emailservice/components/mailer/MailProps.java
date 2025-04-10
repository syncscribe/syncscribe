
package io.syncscribe.emailservice.components.mailer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailProps {
    private String sender;
}
