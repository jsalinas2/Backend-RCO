package com.pierrecode.debtcontrolapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class DebtControlApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebtControlApiApplication.class, args);
    }

}
