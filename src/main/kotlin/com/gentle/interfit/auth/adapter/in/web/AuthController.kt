package com.gentle.interfit.auth.adapter.`in`.web

import com.gentle.interfit.auth.adapter.`in`.web.dto.LoginRequest
import com.gentle.interfit.auth.adapter.`in`.web.dto.LoginResponse
import com.gentle.interfit.auth.adapter.`in`.web.dto.SignUpRequest
import com.gentle.interfit.auth.application.port.`in`.LoginUseCase
import com.gentle.interfit.auth.application.port.`in`.LogoutUseCase
import com.gentle.interfit.auth.application.port.`in`.RefreshUseCase
import com.gentle.interfit.auth.application.port.`in`.SignUpUseCase
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase,
    private val refreshUseCase: RefreshUseCase,
    private val logoutUseCase: LogoutUseCase,
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
    fun login(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<LoginResponse> {

        val result = loginUseCase.login(
            email = request.email,
            password = request.password
        )

        val refreshTokenCookie = createRefreshTokenCookie(refreshToken = result.refreshToken)

        return ResponseEntity.ok()
            .header(
                HttpHeaders.SET_COOKIE,
                refreshTokenCookie.toString()
            )
            .body(
                LoginResponse(
                    accessToken = result.accessToken
                )
            )
    }

    @PostMapping("/refresh")
    fun refresh(
        @CookieValue("refreshToken") refreshToken: String
    ): ResponseEntity<LoginResponse> {

        val result = refreshUseCase.refresh(refreshToken = refreshToken)

        val refreshTokenCookie = createRefreshTokenCookie(refreshToken = result.refreshToken)

        return ResponseEntity.ok()
            .header(
                HttpHeaders.SET_COOKIE,
                refreshTokenCookie.toString()
            )
            .body(
                LoginResponse(
                    accessToken = result.accessToken
                )
            )
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun logout(
        authentication: Authentication,
        response: HttpServletResponse
    ) {
        val userId = authentication.principal as Long

        logoutUseCase.logout(userId)

        val cookie = ResponseCookie
            .from("refreshToken", "")
            .httpOnly(true)
            .secure(false) // 운영에서는 true
            .path("/api/auth")
            .maxAge(Duration.ZERO)
            .sameSite("Strict")
            .build()

        response.addHeader(
            HttpHeaders.SET_COOKIE,
            cookie.toString()
        )
    }

    private fun createRefreshTokenCookie(
        refreshToken: String
    ): ResponseCookie {
        return ResponseCookie
            .from("refreshToken", refreshToken)
            .httpOnly(true)
            .secure(false) // 운영에서는 true
            .path("/api/auth")
            .maxAge(Duration.ofDays(7))
            .sameSite("Strict")
            .build()
    }
}