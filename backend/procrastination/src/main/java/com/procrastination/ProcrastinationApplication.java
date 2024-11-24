package com.procrastination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class ProcrastinationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcrastinationApplication.class, args);
    }

}
