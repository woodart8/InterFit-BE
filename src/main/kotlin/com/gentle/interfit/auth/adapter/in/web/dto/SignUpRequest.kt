package com.gentle.interfit.auth.adapter.`in`.web.dto

import jakarta.validation.constraints.*

data class SignUpRequest(

    @field:NotBlank(message = "이름은 필수입니다.")
    @field:Size(max = 20, message = "이름은 최대 20자까지 가능합니다.")
    val name: String,

    @field:NotBlank(message = "이메일은 필수입니다.")
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @field:Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다.")
    val password: String
)