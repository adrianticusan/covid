package com.covid19.match;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories("com.covid19.match.repositories")
@ComponentScan(basePackages = { "com.covid19.match.*" })
@EntityScan("com.covid19.match.entities")
@EnableScheduling
public class CovidApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CovidApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CovidApplication.class);
    }
}
