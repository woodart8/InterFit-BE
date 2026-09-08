package user.application.port.out

import user.domain.User

interface UserPersistencePort {

    fun save(user: User): User

    fun findById(id: Long): User?

    fun findAll(): List<User>

    fun existsByEmail(email: String): Boolean

    fun deleteById(id: Long)
}