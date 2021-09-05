package com.bindoong.web.config

import com.bindoong.web.dto.ErrorResponse
import com.fasterxml.classmate.TypeResolver
import io.swagger.annotations.ApiOperation
import mu.KLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseMessageBuilder
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.ResponseMessage
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .securityContexts(listOf(securityContext))
            .securitySchemes(listOf(apiKey))
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET, globalResponseMessages)
            .globalResponseMessage(RequestMethod.POST, globalResponseMessages)
            .globalResponseMessage(RequestMethod.DELETE, globalResponseMessages)
            .globalResponseMessage(RequestMethod.PATCH, globalResponseMessages)
            .globalResponseMessage(RequestMethod.PUT, globalResponseMessages)
            .additionalModels(typeResolver.resolve(ErrorResponse::class.java))
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

    private val globalResponseMessages =
        listOf<ResponseMessage>(
            ResponseMessageBuilder().code(400).message("BadRequest").responseModel(errorModel).build(),
            ResponseMessageBuilder().code(401).message("Unauthorized").responseModel(errorModel).build(),
            ResponseMessageBuilder().code(403).message("Forbidden").responseModel(errorModel).build(),
            ResponseMessageBuilder().code(404).message("NotFound").responseModel(errorModel).build()
        )

    class ApiTag {
        companion object {
            const val AUTH = "Auth"
            const val FILE = "File"
            const val POST = "Post"
            const val PROFILE = "Profile"
        }
    }

    companion object : KLogging() {
        val errorModel = ModelRef("ErrorResponse")
        val typeResolver = TypeResolver()
    }
}
