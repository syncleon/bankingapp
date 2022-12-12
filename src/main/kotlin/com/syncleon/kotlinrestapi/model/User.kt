package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.UserEntity
import java.util.stream.Collectors

data class User(
    val id: Long,
    val username: String?,
    val createdAt: String?,
    val currentLimit: Double?,
    val cards: MutableList<Card>
    ) {
    companion object {
        fun toModel(entity: UserEntity): User {
            return User(
                id = entity.id,
                username = entity.username,
                createdAt = entity.createdAt,
                currentLimit = entity.currentLimit,
                cards = entity.cards.stream().map(Card::toModel).collect(Collectors.toList())
            )
        }

        fun toModelGetAll(entity: MutableIterable<UserEntity>): MutableList<User> {
            val userList = mutableListOf<User>()
            for (user in entity) {
                val singleUser = User(
                    id = user.id,
                    username = user.username,
                    createdAt = user.createdAt,
                    currentLimit = user.currentLimit,
                    cards = user.cards.stream().map(Card::toModel).collect(Collectors.toList())
                )
                userList.add(singleUser)
            }
            return userList
        }
    }
}