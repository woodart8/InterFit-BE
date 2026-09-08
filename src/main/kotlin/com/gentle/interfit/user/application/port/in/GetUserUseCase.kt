package com.gentle.interfit.user.application.port.`in`

import com.gentle.interfit.user.domain.User

interface GetUserUseCase {

    fun getUser(id: Long): User

    fun getUsers(): List<User>
}