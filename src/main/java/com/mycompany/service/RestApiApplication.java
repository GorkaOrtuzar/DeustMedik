package com.mycompany.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(scanBasePackages = "com.mycompany")
@SpringBootApplication(scanBasePackages = {"com.mycompany", "com.mycompany.cliente"})
@EnableJpaRepositories(basePackages = "com.mycompany.repositorio")
@EntityScan(basePackages = "com.mycompany.modelo")
public class RestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }
}


