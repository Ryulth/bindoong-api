package com.bindoong.infrastructure.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.ArrayList

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
class R2dbcConfig: AbstractR2dbcConfiguration(){
    override fun connectionFactory(): ConnectionFactory {
        return ConnectionFactories.get("CONNECTION_STRING")
    }

    @Bean
    override fun r2dbcCustomConversions(): R2dbcCustomConversions {
        val converters: MutableList<Converter<*, *>> = ArrayList<Converter<*, *>>()
        return R2dbcCustomConversions(storeConversions, converters)
    }
}