package com.jobsscan.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "website")
@Component
@Getter
public class ScrapProperties {

    @Value("${website.urls}")
    private String url;


    @Value("${website.jobs}")
    private String jobsPathVariable;

    private Map<String, String> filter = new HashMap<>();
}
