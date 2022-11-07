package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.UserEntity

data class User(val id: Long, val username: String) {
    companion object {
        fun toModel(entity: UserEntity): User {
            return User(id = entity.id, username = entity.username)
        }
    }
}