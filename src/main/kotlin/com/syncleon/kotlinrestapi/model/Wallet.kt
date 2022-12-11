package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.WalletEntity

data class Wallet(
    val id:Long,
    val title:String,
    val amount: Double,
    val username: String?,
    val desc:String?,
    val createdAt: String?
    ) {
    companion object {
        fun toModel(entity: WalletEntity): Wallet {
            return Wallet(
                id = entity.id,
                title = entity.title,
                amount = entity.amount,
                username = entity.user?.username,
                desc = entity.description,
                createdAt = entity.createdAt
            )
        }

        fun toModelGetAll(entity: MutableIterable<WalletEntity>): MutableList<Wallet> {
            val walletList = mutableListOf<Wallet>()
            for (wallet in entity) {
                val singleWallet = Wallet(
                    username = wallet.user?.username,
                    id = wallet.id,
                    title = wallet.title,
                    amount = wallet.amount,
                    desc = wallet.description,
                    createdAt = wallet.createdAt
                )
                walletList.add(singleWallet)
            }
            return walletList
        }
    }
}
