package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.WalletEntity

data class Wallet (
    private val id:Long,
    private val title:String,
    private val amount:Double
    ) {

    companion object {
        fun toModel(entity: WalletEntity): Wallet {
            return Wallet(id = entity.id, title = entity.title, amount = entity.amount)
        }
    }
}