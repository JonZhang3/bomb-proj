package com.bombproj.config

import javax.annotation.Resource
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.{ResourceHandlerRegistry, WebMvcConfigurer}

@Component
@Configuration
class WebMvcConfig @Resource()(appProperties: AppProperties) extends WebMvcConfigurer{

    override def addResourceHandlers(registry: ResourceHandlerRegistry): Unit = {
        registry.addResourceHandler("/static/**")
            .addResourceLocations(
                "file:" + appProperties.coverImagePath,
                "classpath:/META‐INF/resources/",
                "classpath:/resources/",
                "classpath:/static/",
                "classpath:/public/",
                "/"
            )
    }

}
