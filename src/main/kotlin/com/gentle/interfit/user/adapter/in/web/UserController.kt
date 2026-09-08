package com.gentle.interfit.user.adapter.`in`.web

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import com.gentle.interfit.user.adapter.`in`.web.dto.UpdateUserRequest
import com.gentle.interfit.user.adapter.`in`.web.dto.UserResponse
import com.gentle.interfit.user.application.port.`in`.DeleteUserUseCase
import com.gentle.interfit.user.application.port.`in`.GetUserUseCase
import com.gentle.interfit.user.application.port.`in`.UpdateUserUseCase
import com.gentle.interfit.user.domain.User

@RestController
@RequestMapping("/api/users")
class UserController(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) {
    @GetMapping("/{id}")
    fun getUser(
        @PathVariable id: Long
    ): UserResponse {

        return getUserUseCase
            .getUser(id)
            .toResponse()
    }

    @GetMapping
    fun getUsers(): List<UserResponse> {

        return getUserUseCase
            .getUsers()
            .map { it.toResponse() }
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateUserRequest
    ): UserResponse {

        return updateUserUseCase
            .updateUser(id, request.name)
            .toResponse()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(
        @PathVariable id: Long
    ) {

        deleteUserUseCase.deleteUser(id)
    }

    private fun User.toResponse(): UserResponse {
        return UserResponse(
            id = id!!,
            name = name,
            email = email
        )
    }
}