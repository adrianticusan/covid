package com.covid19.match.configs.mail;

import com.covid19.match.configs.deployment.UsaDeployment;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "usa-mail")
@Conditional(UsaDeployment.class)
@Data
public class UsaMailConfiguration implements MailConfiguration {
    private String mailFrom;
}