package com.bindoong.web.config

import com.bindoong.web.dto.FacebookLoginRequest
import com.bindoong.web.dto.ImageDto
import com.fasterxml.classmate.TypeResolver
import io.swagger.annotations.ApiOperation
import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.codec.multipart.FilePart
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux

@Configuration
@EnableSwagger2WebFlux
class SwaggerConfig {
    @Bean
    fun api(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .securityContexts(listOf(securityContext))
            .securitySchemes(listOf(apiKey))
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation::class.java))
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

    companion object : KLogging() {
        val typeResolver = TypeResolver()

    }
}
