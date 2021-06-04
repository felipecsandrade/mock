package com.example.mock.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("uri")
public class UrlProperties {

    private String portal;
}
