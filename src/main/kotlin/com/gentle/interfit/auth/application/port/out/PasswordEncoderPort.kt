package com.gentle.interfit.auth.application.port.out

interface PasswordEncoderPort {

    fun encode(password: String): String

    fun matches(
        rawPassword: String,
        encodedPassword: String
    ): Boolean
}