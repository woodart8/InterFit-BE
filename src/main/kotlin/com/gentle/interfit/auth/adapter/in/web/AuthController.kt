package com.gentle.interfit.auth.adapter.`in`.web

import com.gentle.interfit.auth.adapter.`in`.web.dto.LoginRequest
import com.gentle.interfit.auth.adapter.`in`.web.dto.SignUpRequest
import com.gentle.interfit.auth.application.port.`in`.LoginUseCase
import com.gentle.interfit.auth.application.port.`in`.SignUpUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(
        @Valid @RequestBody request: SignUpRequest
    ) {

        signUpUseCase.signUp(
            name = request.name,
            email = request.email,
            password = request.password
        )
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(
        @Valid @RequestBody request: LoginRequest
    ) {
        loginUseCase.login(
            email = request.email,
            password = request.password
        )
    }
}