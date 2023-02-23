package com.reactive.student.infrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "server")
public class ServerProperties {
    private Map<String, TypeError> errors;

    @Getter
    @Setter
    public static class TypeError {
        private String code;
        private String message;
        private String httpStatus;
    }
}
