package com.covid19.match.configs;

import com.covid19.match.configs.deployment.EuropeDeployment;
import com.covid19.match.configs.deployment.UsaDeployment;
import com.covid19.match.enums.DistanceUnit;
import com.covid19.match.external.amazon.enums.Regions;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ProjectConfigs {
    @Bean
    Gson gson() {
        return new Gson();
    }


    @Bean
    public ResourceBundleMessageSource messageSource() {

        var source = new ResourceBundleMessageSource();
        source.setBasenames("messages/messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    @Conditional(EuropeDeployment.class)
    public DistanceUnit getDistanceUnitEurope() {
        return DistanceUnit.KM;
    }

    @Bean
    @Conditional(UsaDeployment.class)
    public DistanceUnit getDistanceUnitUS() {
        return DistanceUnit.MILES;
    }
}
