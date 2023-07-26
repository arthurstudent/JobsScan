package com.jobsscan.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties()
@Component
@Getter
public class GeocoderProperties {

    @Value("${geocoder.hereapi.api.key}")
    private String apiKey;

    @Value("${geocoder.resource}")
    private String resource;

}
