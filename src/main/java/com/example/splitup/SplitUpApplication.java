package com.example.splitup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SplitUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplitUpApplication.class, args);
    }

}
