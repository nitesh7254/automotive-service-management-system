package com.nitesh.autosupport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.nitesh.autosupport",
        "controller",
        "service",
        "repository",
        "model",
        "ai",
        "exception",
        "validation"
})
@EntityScan(basePackages = "model")
@EnableJpaRepositories(basePackages = "repository")
public class AutomativeAiSupportApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                AutomativeAiSupportApplication.class,
                args
        );
    }
}