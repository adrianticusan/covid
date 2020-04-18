package com.covid19.match.configs;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AmazonConfig {

    @Bean
    AmazonSimpleEmailService amazonSimpleEmailService() {
        AWSCredentialsProvider awsCredentialsProvider = new ClasspathPropertiesFileCredentialsProvider("awsCredentials.properties");
        AmazonSimpleEmailServiceAsyncClientBuilder amazonSimpleEmailServiceClientBuilder = AmazonSimpleEmailServiceAsyncClient.asyncBuilder();
        amazonSimpleEmailServiceClientBuilder.setCredentials(awsCredentialsProvider);
        amazonSimpleEmailServiceClientBuilder.withRegion(Regions.EU_WEST_1);
        return amazonSimpleEmailServiceClientBuilder.build();
    }
}
