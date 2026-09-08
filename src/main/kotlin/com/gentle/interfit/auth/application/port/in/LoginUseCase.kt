package com.gentle.interfit.auth.application.port.`in`

import com.gentle.interfit.auth.application.dto.LoginResult

interface LoginUseCase {

    fun login(
        email: String,
        password: String
    ): LoginResult
}