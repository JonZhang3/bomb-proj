package com.bombproj.config

import javax.validation.constraints.{NotEmpty, NotNull}
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated

import scala.beans.BeanProperty

@Configuration
@ConfigurationProperties(prefix = "app")
@Validated
class AppProperties {

    @BeanProperty
    @NotNull
    @NotEmpty
    var aesKey: String = _

    @BeanProperty
    @NotNull
    @NotEmpty
    var coverImagePath: String = _

    @BeanProperty
    @NotNull
    var maxPageSize: Int = _

}
