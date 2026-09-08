package com.gentle.interfit.user.application.port.`in`

import com.gentle.interfit.user.domain.User

interface UpdateUserUseCase {

    fun updateUser(
        id: Long,
        name: String
    ): User
}