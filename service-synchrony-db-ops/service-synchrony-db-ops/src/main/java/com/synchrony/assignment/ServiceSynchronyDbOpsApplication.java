package com.synchrony.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main entry point of the application.
 * - Bootstraps the service using the Spring Boot framework.
 * - Annotation-driven features:
 * - @SpringBootApplication: Enables key Spring Boot auto-configurations.
 * - @EnableCaching: Enables caching support.
 */

@SpringBootApplication
@EnableCaching
public class ServiceSynchronyDbOpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceSynchronyDbOpsApplication.class, args);
    }

}
