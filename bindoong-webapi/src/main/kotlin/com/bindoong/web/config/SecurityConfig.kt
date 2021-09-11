package com.bindoong.web.config

import com.bindoong.web.security.TokenAuthenticationManager
import com.bindoong.web.security.TokenAuthorizationFilter
import com.bindoong.web.security.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val authenticationManager: TokenAuthenticationManager
) {
    @Bean
    fun applicationSecurityWebFilterChain(
        http: ServerHttpSecurity,
    ): SecurityWebFilterChain {
        http.authenticationManager(authenticationManager)
            .addFilterBefore(
                TokenAuthorizationFilter(authenticationManager, tokenProvider),
                SecurityWebFiltersOrder.FORM_LOGIN
            )

        http.apply {
            securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            formLogin { it.disable() }
            logout { it.disable() }
            httpBasic { it.disable() }
            csrf { it.disable() }
        }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowCredentials = false
            allowedOrigins = listOf("*")
            allowedMethods = listOf("*")
            allowedHeaders = listOf("*")
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }
    }
}
