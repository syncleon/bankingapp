package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.UserEntity
import java.util.stream.Collectors

data class User(
    val id: Long,
    val username: String,
    val created_at: String?,
    val wallets: MutableList<Wallet>
    ) {
    companion object {
        fun toModel(entity: UserEntity): User {
            return User(
                id = entity.id,
                username = entity.username,
                created_at = entity.created_at,
                wallets = entity.wallets.stream().map(Wallet::toModel).collect(Collectors.toList())
            )
        }

        fun toModelGetAll(entity: MutableIterable<UserEntity>): MutableList<User> {
            val userList = mutableListOf<User>()
            for (user in entity) {
                val singleUser = User(
                    id = user.id,
                    username = user.username,
                    created_at = user.created_at,
                    wallets = user.wallets.stream().map(Wallet::toModel).collect(Collectors.toList())
                )
                userList.add(singleUser)
            }
            return userList
        }
    }
}