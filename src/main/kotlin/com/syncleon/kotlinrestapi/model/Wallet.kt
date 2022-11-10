package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.WalletEntity

@Suppress("NAME_SHADOWING")
data class Wallet(
    val id:Long,
    val title:String,
    val amount: Double,
    val username: String?
    ) {
    companion object {
        fun toModel(entity: WalletEntity): Wallet {
            return Wallet(
                id = entity.id,
                title = entity.title,
                amount = entity.amount,
                username = entity.user?.username
            )
        }

        fun toModelGetAll(entity: MutableIterable<WalletEntity>): MutableList<Wallet> {
            val walletList = mutableListOf<Wallet>()
            for (wallet in entity) {
                val wallet = Wallet(
                    username = wallet.user?.username,
                    id = wallet.id,
                    title = wallet.title,
                    amount = wallet.amount,
                )
                walletList.add(wallet)
            }
            return walletList
        }
    }
}
