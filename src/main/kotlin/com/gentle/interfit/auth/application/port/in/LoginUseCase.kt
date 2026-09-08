package com.gentle.interfit.auth.application.port.`in`

interface LoginUseCase {

    fun login(
        email: String,
        password: String
    )
}