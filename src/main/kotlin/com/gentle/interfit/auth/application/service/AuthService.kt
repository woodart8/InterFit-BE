package com.gentle.interfit.auth.application.service

import com.gentle.interfit.auth.application.dto.LoginResult
import com.gentle.interfit.auth.application.dto.RefreshResult
import com.gentle.interfit.auth.application.port.`in`.LoginUseCase
import com.gentle.interfit.auth.application.port.`in`.LogoutUseCase
import com.gentle.interfit.auth.application.port.`in`.RefreshUseCase
import com.gentle.interfit.auth.application.port.`in`.SignUpUseCase
import com.gentle.interfit.auth.application.port.out.PasswordEncoderPort
import com.gentle.interfit.auth.application.port.out.RefreshTokenPort
import com.gentle.interfit.auth.application.port.out.TokenProviderPort
import com.gentle.interfit.common.exception.BusinessException
import com.gentle.interfit.common.exception.ErrorCode
import com.gentle.interfit.user.application.port.out.UserPersistencePort
import com.gentle.interfit.user.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val userPersistencePort: UserPersistencePort,
    private val passwordEncoderPort: PasswordEncoderPort,
    private val tokenProviderPort: TokenProviderPort,
    private val refreshTokenPort: RefreshTokenPort
) : SignUpUseCase, LoginUseCase, RefreshUseCase, LogoutUseCase {

    @Transactional
    override fun signUp(
        name: String,
        email: String,
        password: String
    ) {

        if (userPersistencePort.existsByEmail(email)) {
            throw BusinessException(ErrorCode.DUPLICATE_EMAIL)
        }

        val encodedPassword = passwordEncoderPort.encode(password)

        val user = User(
            name = name,
            email = email,
            password = encodedPassword
        )

        userPersistencePort.save(user)
    }

    override fun login(
        email: String,
        password: String
    ): LoginResult {

        val user = userPersistencePort.findByEmail(email)
            ?: throw BusinessException(ErrorCode.INVALID_CREDENTIALS)

        if (!passwordEncoderPort.matches(password, user.password)) {
            throw BusinessException(ErrorCode.INVALID_CREDENTIALS)
        }

        val accessToken = tokenProviderPort.generateAccessToken(
            userId = user.id
                ?: throw IllegalStateException("사용자 ID가 존재하지 않습니다."),
            email = user.email,
            role = user.role
        )

        val refreshToken = tokenProviderPort.generateRefreshToken(userId = user.id)

        refreshTokenPort.save(
            userId = user.id,
            refreshToken = refreshToken
        )

        return LoginResult(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun refresh(refreshToken: String): RefreshResult {

        if (!tokenProviderPort.validateToken(refreshToken)) {
            throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)
        }

        if (tokenProviderPort.getTokenType(refreshToken) != "refresh") {
            throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)
        }

        val userId = tokenProviderPort.getUserId(refreshToken)

        val savedToken = refreshTokenPort.findByUserId(userId)
            ?: throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)

        if (savedToken != refreshToken) {
            throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)
        }

        val user = userPersistencePort.findById(userId)
            ?: throw BusinessException(ErrorCode.USER_NOT_FOUND)

        val newAccessToken = tokenProviderPort.generateAccessToken(
            userId = userId,
            email = user.email,
            role = user.role
        )

        val newRefreshToken = tokenProviderPort.generateRefreshToken(
            userId = userId
        )

        refreshTokenPort.save(
            userId = userId,
            refreshToken = newRefreshToken
        )

        return RefreshResult(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    override fun logout(userId: Long) {
        refreshTokenPort.delete(userId)
    }
}