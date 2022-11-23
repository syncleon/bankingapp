package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.WalletEntity

data class Wallet(
    val id:Long,
    val title:String,
    val balance: Double,
    val username: String?,
    val desc:String?,
    val created_at: String?
    ) {
    companion object {
        fun toModel(entity: WalletEntity): Wallet {
            return Wallet(
                id = entity.id,
                title = entity.title,
                balance = entity.balance,
                username = entity.user?.username,
                desc = entity.description,
                created_at = entity.created_at
            )
        }

        fun toModelGetAll(entity: MutableIterable<WalletEntity>): MutableList<Wallet> {
            val walletList = mutableListOf<Wallet>()
            for (wallet in entity) {
                val singleWallet = Wallet(
                    username = wallet.user?.username,
                    id = wallet.id,
                    title = wallet.title,
                    balance = wallet.balance,
                    desc = wallet.description,
                    created_at = wallet.created_at
                )
                walletList.add(singleWallet)
            }
            return walletList
        }
    }
}
