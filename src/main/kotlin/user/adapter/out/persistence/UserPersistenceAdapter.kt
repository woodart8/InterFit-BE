package user.adapter.out.persistence

import org.springframework.stereotype.Component
import user.application.port.out.UserPersistencePort
import user.domain.User

@Component
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserPersistencePort {

    override fun save(user: User): User {

        val entity = if (user.id == null) {
            UserEntity(
                name = user.name,
                email = user.email
            )
        } else {
            userJpaRepository.findById(user.id)
                .orElseThrow {
                    IllegalArgumentException("사용자를 찾을 수 없습니다.")
                }
                .apply {
                    name = user.name
                }
        }

        return userJpaRepository.save(entity).toDomain()
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }

    override fun findById(id: Long): User? {
        return userJpaRepository.findById(id)
            .orElse(null)
            ?.toDomain()
    }

    override fun findAll(): List<User> {
        return userJpaRepository.findAll()
            .map { it.toDomain() }
    }

    override fun deleteById(id: Long) {
        userJpaRepository.deleteById(id)
    }

    private fun UserEntity.toDomain(): User {
        return User(
            id = id,
            name = name,
            email = email
        )
    }
}