package com.gentle.interfit.auth.application.service

import com.gentle.interfit.auth.application.port.`in`.LoginUseCase
import com.gentle.interfit.auth.application.port.`in`.SignUpUseCase
import com.gentle.interfit.auth.application.port.out.PasswordEncoderPort
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
    private val passwordEncoderPort: PasswordEncoderPort
) : SignUpUseCase, LoginUseCase {

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
    ) {

        val user = userPersistencePort.findByEmail(email)
            ?: throw BusinessException(ErrorCode.INVALID_CREDENTIALS)

        if (!passwordEncoderPort.matches(password, user.password)) {
            throw BusinessException(ErrorCode.INVALID_CREDENTIALS)
        }
    }
}