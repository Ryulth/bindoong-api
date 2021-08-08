package com.bindoong.web.config

import io.swagger.v3.oas.annotations.Operation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {
    @Bean
    fun api() =
        Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(Operation::class.java))
            .build()

    private val apiInfo: ApiInfo = ApiInfoBuilder()
        .title("Bindoong API Documentation")
        .description("Rest API For Bindoong Service")
        .build()
}
