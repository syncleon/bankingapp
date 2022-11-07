package com.syncleon.kotlinrestapi.repository

import com.syncleon.kotlinrestapi.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepo: CrudRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity
}