package com.gentle.interfit.auth.adapter.out.security

import com.gentle.interfit.auth.application.port.out.RefreshTokenPort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisRefreshTokenAdapter(
    private val redisTemplate: StringRedisTemplate
) : RefreshTokenPort {

    companion object {
        private const val PREFIX = "auth:refresh:"
        private const val REFRESH_TOKEN_TTL = 604800L
    }

    override fun save(
        userId: Long,
        refreshToken: String
    ) {
        redisTemplate.opsForValue().set(
            PREFIX + userId,
            refreshToken,
            Duration.ofSeconds(REFRESH_TOKEN_TTL)
        )
    }

    override fun findByUserId(userId: Long): String? {
        return redisTemplate.opsForValue()
            .get(PREFIX + userId)
    }

    override fun delete(userId: Long) {
        redisTemplate.delete(PREFIX + userId)
    }
}