package com.gentle.interfit.auth.adapter.out.security

import com.gentle.interfit.auth.application.port.out.PasswordEncoderPort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderAdapter : PasswordEncoderPort {

    private val passwordEncoder = BCryptPasswordEncoder()

    override fun encode(password: String): String {
        return passwordEncoder.encode(password)
            ?: throw IllegalStateException("비밀번호 암호화에 실패했습니다.")
    }

    override fun matches(
        rawPassword: String,
        encodedPassword: String
    ): Boolean {
        return passwordEncoder.matches(
            rawPassword,
            encodedPassword
        )
    }
}