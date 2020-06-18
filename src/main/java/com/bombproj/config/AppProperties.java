package com.bombproj.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "app")
@Validated
@Data
public class AppProperties {

    @NotNull
    @NotEmpty
    private String aesKey;

    @NotNull
    @NotEmpty
    private String coverImagePath;

}
