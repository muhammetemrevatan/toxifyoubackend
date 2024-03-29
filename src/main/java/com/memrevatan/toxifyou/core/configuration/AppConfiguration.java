package com.memrevatan.toxifyou.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "my.server")
public class AppConfiguration {
    private String uploadPath;
}
