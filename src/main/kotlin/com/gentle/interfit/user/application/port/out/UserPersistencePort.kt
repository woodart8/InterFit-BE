package com.gentle.interfit.user.application.port.out

import com.gentle.interfit.user.domain.User

interface UserPersistencePort {

    fun save(user: User): User

    fun findById(id: Long): User?

    fun findByEmail(email: String): User?

    fun findAll(): List<User>

    fun existsByEmail(email: String): Boolean

    fun deleteById(id: Long)
}