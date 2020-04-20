package com.covid19.match.amazon.client.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.covid19.match.amazon.enums.Regions;
import com.covid19.match.configs.deployment.EuropeDeployment;
import com.covid19.match.configs.deployment.UsaDeployment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import static com.amazonaws.regions.Regions.EU_WEST_2;
import static com.amazonaws.regions.Regions.US_WEST_2;

@Configuration
public class AmazonConfig {
    private AWSCredentialsProvider awsCredentialsProvider;

    public AmazonConfig() {
        awsCredentialsProvider = new ClasspathPropertiesFileCredentialsProvider("awsCredentials.properties");
    }


    @Bean
    @Conditional(UsaDeployment.class)
    AmazonSimpleEmailService amazonSimpleEmailServiceUSA() {
        AmazonSimpleEmailServiceAsyncClientBuilder amazonSimpleEmailServiceClientBuilder = AmazonSimpleEmailServiceAsyncClient.asyncBuilder();
        amazonSimpleEmailServiceClientBuilder.setCredentials(awsCredentialsProvider);
        amazonSimpleEmailServiceClientBuilder.withRegion(US_WEST_2);
        return amazonSimpleEmailServiceClientBuilder.build();
    }

    @Bean
    @Conditional(EuropeDeployment.class)
    AmazonSimpleEmailService amazonSimpleEmailServiceEurope() {
        AmazonSimpleEmailServiceAsyncClientBuilder amazonSimpleEmailServiceClientBuilder = AmazonSimpleEmailServiceAsyncClient.asyncBuilder();
        amazonSimpleEmailServiceClientBuilder.setCredentials(awsCredentialsProvider);
        amazonSimpleEmailServiceClientBuilder.withRegion(com.amazonaws.regions.Regions.EU_WEST_2);
        return amazonSimpleEmailServiceClientBuilder.build();
    }

    @Bean
    @Conditional(UsaDeployment.class)
    AmazonSQS getUsaSQSClient() {
        return AmazonSQSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(US_WEST_2)
                .build();
    }

    @Bean
    @Conditional(EuropeDeployment.class)
    AmazonSQS getEuropeSQSClient() {
        return AmazonSQSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(EU_WEST_2)
                .build();
    }

    @Bean
    @Conditional(EuropeDeployment.class)
    public Regions getRegionEurope() {
        return Regions.EUROPE;
    }

    @Bean
    @Conditional(UsaDeployment.class)
    public Regions getRegionUsa() {
        return Regions.USA;
    }
}
