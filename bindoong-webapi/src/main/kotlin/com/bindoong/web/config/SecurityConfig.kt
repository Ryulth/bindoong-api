package com.bindoong.web.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig (
) {
    @Bean
    fun applicationSecurityWebFilterChain(
        http: ServerHttpSecurity,
    ): SecurityWebFilterChain {
//        http
////            .addFilterAt(
////                TokenAuthorizationFilter(authenticationManager, tokenProvider),
////                SecurityWebFiltersOrder.HTTP_BASIC
////            )
//            .securityMatcher(PathPatternParserServerWebExchangeMatcher("/**"))
//            .authorizeExchange()
//            .pathMatchers(
//                HealthCheckController.BASE_URL,
//            ).permitAll()
//            .pathMatchers(
//                "/v3/api-docs/**",
//                "/swagger-ui.html",
//                "/webjars/**",
//                "/actuator/**",
//            ).permitAll()
//            .anyExchange()
//            .authenticated()

        http.apply {
            securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            formLogin { it.disable() }
            logout { it.disable() }
            httpBasic { it.disable() }
            csrf { it.disable() }
        }

        return http.build()
    }
}
