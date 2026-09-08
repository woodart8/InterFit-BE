package com.gentle.interfit.user.application.service

import com.gentle.interfit.common.exception.BusinessException
import com.gentle.interfit.common.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import user.application.port.`in`.DeleteUserUseCase
import user.application.port.`in`.GetUserUseCase
import user.application.port.`in`.UpdateUserUseCase
import user.application.port.out.UserPersistencePort
import user.domain.User

@Service
@Transactional(readOnly = true)
class UserService(
    private val userPersistencePort: UserPersistencePort
) : GetUserUseCase,
    UpdateUserUseCase,
    DeleteUserUseCase {

    override fun getUser(id: Long): User {
        return userPersistencePort.findById(id)
            ?: throw BusinessException(ErrorCode.USER_NOT_FOUND)
    }

    override fun getUsers(): List<User> {
        return userPersistencePort.findAll()
    }

    @Transactional
    override fun updateUser(
        id: Long,
        name: String
    ): User {

        val user = userPersistencePort.findById(id)
            ?: throw BusinessException(ErrorCode.USER_NOT_FOUND)

        user.updateName(name)

        return userPersistencePort.save(user)
    }

    @Transactional
    override fun deleteUser(id: Long) {

        userPersistencePort.findById(id)
            ?: throw BusinessException(ErrorCode.USER_NOT_FOUND)

        userPersistencePort.deleteById(id)
    }
}