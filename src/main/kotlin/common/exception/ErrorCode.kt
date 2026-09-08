package common.exception

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
    )
}