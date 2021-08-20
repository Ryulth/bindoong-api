package com.bindoong.web.config

import io.swagger.v3.oas.annotations.Operation
import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {
    @Bean
    fun api(): Docket =
        Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo)
            .securityContexts(listOf(securityContext))
            .securitySchemes(listOf(apiKey))
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(Operation::class.java))
            .build()

    private val securityContext =
        SecurityContext.builder().securityReferences(
            listOf(
                SecurityReference(HttpHeaders.AUTHORIZATION, arrayOf(AuthorizationScope("global", "accessEverything")))
            )
        ).build()

    private val apiKey = ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header")
    private val apiInfo: ApiInfo = ApiInfoBuilder()
        .title("Bindoong API Documentation")
        .description("Rest API For Bindoong Service")
        .build()

    companion object : KLogging()
}
