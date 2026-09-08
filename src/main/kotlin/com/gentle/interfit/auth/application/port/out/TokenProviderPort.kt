package com.gentle.interfit.auth.application.port.out

interface TokenProviderPort {

    fun generateAccessToken(
        userId: Long,
        email: String
    ): String

    fun generateRefreshToken(
        userId: Long
    ): String

    fun validateToken(token: String): Boolean

    fun getUserId(token: String): Long

    fun getTokenType(token: String): String
}