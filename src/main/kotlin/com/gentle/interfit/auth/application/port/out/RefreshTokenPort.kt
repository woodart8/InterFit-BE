package com.gentle.interfit.auth.application.port.out

interface RefreshTokenPort {

    fun save(userId: Long, refreshToken: String)

    fun findByUserId(userId: Long): String?

    fun delete(userId: Long)
}