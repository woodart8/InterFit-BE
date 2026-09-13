package com.gentle.interfit.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class FastApiConfig {

    @Bean
    fun fastApiWebClient(
        @Value("\${fastapi.base-url}") baseUrl: String
    ): WebClient {
        return WebClient.builder()
            .baseUrl(baseUrl)
            .build()
    }
}