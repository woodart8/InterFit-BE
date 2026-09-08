package com.gentle.interfit.user.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): UserEntity?
}