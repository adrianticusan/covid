package com.covid19.match.configs;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfigs {
    @Bean
    Gson gson() {
        return new Gson();
    }
}
