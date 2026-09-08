package com.gentle.interfit.auth.adapter.out.security

import com.gentle.interfit.auth.application.port.out.TokenProviderPort
import com.gentle.interfit.common.exception.BusinessException
import com.gentle.interfit.common.exception.ErrorCode
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date
import kotlin.getValue

@Component
class JwtTokenAdapter(
    @Value("\${jwt.secret}")
    private val secret: String,

    @Value("\${jwt.access-token-expiration}")
    private val accessTokenExpiration: Long,

    @Value("\${jwt.refresh-token-expiration}")
    private val refreshTokenExpiration: Long
) : TokenProviderPort {

    private val key by lazy {
        Keys.hmacShaKeyFor(
            secret.toByteArray(StandardCharsets.UTF_8)
        )
    }

    override fun generateAccessToken(
        userId: Long,
        email: String
    ): String {

        return generateToken(
            userId = userId,
            expiration = accessTokenExpiration,
            claims = mapOf(
                "email" to email,
                "type" to "access"
            )
        )
    }

    override fun generateRefreshToken(
        userId: Long
    ): String {

        return generateToken(
            userId = userId,
            expiration = refreshTokenExpiration,
            claims = mapOf(
                "type" to "refresh"
            )
        )
    }

    override fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)

            true
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    override fun getUserId(token: String): Long {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
            .toLong()
    }

    override fun getTokenType(token: String): String {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload["type"]
            ?.toString()
            ?: throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)
    }

    private fun generateToken(
        userId: Long,
        expiration: Long,
        claims: Map<String, Any>
    ): String {

        val now = Date()

        return Jwts.builder()
            .subject(userId.toString())
            .claims(claims)
            .issuedAt(now)
            .expiration(Date(now.time + expiration))
            .signWith(key)
            .compact()
    }
}