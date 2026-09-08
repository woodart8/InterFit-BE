package com.gentle.interfit.user.application.port.`in`

import user.domain.User

interface GetUserUseCase {

    fun getUser(id: Long): User

    fun getUsers(): List<User>
}