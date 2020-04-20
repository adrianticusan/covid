package com.covid19.match.configs.mail;

import com.covid19.match.configs.deployment.EuropeDeployment;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(EuropeDeployment.class)
@ConfigurationProperties(prefix = "europe-mail")
@Data
public class EuropeMailConfiguration implements MailConfiguration {
    private String mailFrom;
}
