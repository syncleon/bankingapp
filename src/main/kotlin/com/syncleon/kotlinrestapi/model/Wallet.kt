package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.WalletEntity

data class Wallet(
    val id:Long,
    val title:String,
    val balance: Double,
    val username: String?,
    val desc:String?
    ) {
    companion object {
        fun toModel(entity: WalletEntity): Wallet {
            return Wallet(
                id = entity.id,
                title = entity.title,
                balance = entity.balance,
                username = entity.user?.username,
                desc = entity.description
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
                    desc = wallet.description
                )
                walletList.add(singleWallet)
            }
            return walletList
        }
    }
}
