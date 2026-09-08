package com.gentle.interfit.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {

    USER_NOT_FOUND(
        HttpStatus.NOT_FOUND,
        "사용자를 찾을 수 없습니다."
    ),

    DUPLICATE_EMAIL(
        HttpStatus.CONFLICT,
        "이미 사용 중인 이메일입니다."
    ),

    INVALID_CREDENTIALS(
        HttpStatus.UNAUTHORIZED,
        "이메일 또는 비밀번호가 올바르지 않습니다."
    ),

    INVALID_REFRESH_TOKEN(
        HttpStatus.UNAUTHORIZED,
        "유효하지 않은 Refresh Token입니다."
    )
}