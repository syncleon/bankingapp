package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.CardEntity
import com.syncleon.kotlinrestapi.entity.TransactionEntity

data class Card (
    val id:Long,
    val title:String,
    val cardLimit: Double,
    val username: String?,
    val createdAt: String?
    ) {
    companion object {
        fun toModel(cardEntity: CardEntity): Card {
            return Card(
                id = cardEntity.id,
                title = cardEntity.cardName,
                cardLimit = cardEntity.cardLimit,
                username = cardEntity.user?.username,
                createdAt = cardEntity.createdAt,
            )
        }

        fun toModelGetAll(entityList: MutableIterable<CardEntity>): MutableList<Card> {
            val cardList = mutableListOf<Card>()
            for (cardEntity in entityList) {
                val singleCard = Card(
                    username = cardEntity.user?.username,
                    id = cardEntity.id,
                    title = cardEntity.cardName,
                    cardLimit = cardEntity.cardLimit,
                    createdAt = cardEntity.createdAt,
                )
                cardList.add(singleCard)
            }
            return cardList
        }
    }
}
