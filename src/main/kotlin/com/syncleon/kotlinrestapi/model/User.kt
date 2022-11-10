package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.UserEntity
import java.util.stream.Collectors

@Suppress("NAME_SHADOWING")
data class User(
    val id: Long,
    val username: String,
    val wallets: MutableList<Wallet>
    ) {
    companion object {
        fun toModel(entity: UserEntity): User {
            return User(
                id = entity.id,
                username = entity.username,
                wallets = entity.wallets.stream().map(Wallet::toModel).collect(Collectors.toList()))
        }

        fun toModelGetAll(entity: MutableIterable<UserEntity>): MutableList<User> {
            val userList = mutableListOf<User>()
            for (user in entity) {
                val user = User(
                    id = user.id,
                    username = user.username,
                    wallets = user.wallets.stream().map(Wallet::toModel).collect(Collectors.toList())
                )
                userList.add(user)
            }
            return userList
        }
    }
}