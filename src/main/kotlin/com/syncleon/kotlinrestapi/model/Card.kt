package com.syncleon.kotlinrestapi.model

import com.syncleon.kotlinrestapi.entity.CardEntity

data class Card(
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
                createdAt = cardEntity.createdAt
            )
        }

        fun toModelGetAll(entityList: MutableIterable<CardEntity>): MutableList<Card> {
            val cardList = mutableListOf<Card>()
            for (entity in entityList) {
                val singleCard = Card(
                    username = entity.user?.username,
                    id = entity.id,
                    title = entity.cardName,
                    cardLimit = entity.cardLimit,
                    createdAt = entity.createdAt
                )
                cardList.add(singleCard)
            }
            return cardList
        }
    }
}
