package com.gentle.interfit.auth.application.port.`in`

interface SignUpUseCase {

    fun signUp(
        name: String,
        email: String,
        password: String
    )
}