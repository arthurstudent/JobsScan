package com.jobsscan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
public class JobsScanApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobsScanApplication.class, args);
    }

}
