package com.bombproj.config

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.cors.{CorsConfiguration, UrlBasedCorsConfigurationSource}
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    def corsFilter(): CorsFilter = {
        val config = new CorsConfiguration
        config.addAllowedOrigin("*")
        config.setAllowCredentials(true)
        config.addAllowedMethod("*")
        config.addAllowedHeader("*")
        val configurationSource = new UrlBasedCorsConfigurationSource
        configurationSource.registerCorsConfiguration("/**", config)
        new CorsFilter(configurationSource)
    }

}
