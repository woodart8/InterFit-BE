package user.adapter.`in`.web.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateUserRequest(
    @field:NotBlank(message = "이름은 필수입니다.")
    @field:Size(max = 20, message = "이름은 최대 20자까지 가능합니다.")
    val name: String
)