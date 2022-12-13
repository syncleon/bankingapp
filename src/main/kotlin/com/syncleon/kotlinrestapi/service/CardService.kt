package com.syncleon.kotlinrestapi.service

import com.syncleon.kotlinrestapi.entity.CardEntity
import com.syncleon.kotlinrestapi.exception.NotEnoughMoneyException
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.exception.CardNotFoundException
import com.syncleon.kotlinrestapi.model.Card
import com.syncleon.kotlinrestapi.repository.UserRepo
import com.syncleon.kotlinrestapi.repository.CardRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Service
class CardService(

    @Autowired
    val cardRepo: CardRepo,
    @Autowired
    val userRepo:UserRepo,

    ) {
    fun addCard(cardEntity: CardEntity, userId: Long): CardEntity {
        val user = userRepo.findById(userId)
        if (user.isEmpty) {
            throw UserNotFoundException("Card cant be added to not created user")
        }
        val userCards = userRepo.findById(userId).get().cards
        val cardNames = userCards.map { it.cardName }
        when (
            val cardName = cardEntity.cardName
        ) {
            in cardNames -> throw Exception("$cardName name already exist, select another name")
        }
        cardEntity.user = user.get()
        cardEntity.createdAt = System.currentTimeMillis().toString()
        return cardRepo.save(cardEntity)
    }

    fun getAllCards(): MutableList<Card> {
        return Card.toModelGetAll(cardRepo.findAll())
    }

    fun getCard(cardId: Long): Card{
        return Card.toModel(cardRepo.findById(cardId).get())

    }

    fun amountCard(cardId: Long, userId: Long, cardEntity: CardEntity): CardEntity? {
        val singleCard = cardRepo.findById(cardId)
            .orElseThrow {
                CardNotFoundException("Card with id {$cardId} not found.")
            }
        val singleUser = userRepo.findById(userId)
            .orElseThrow {
                UserNotFoundException("User with id: $userId not found")
            }

        var userLimit = singleUser.currentLimit
        val amountValue = cardEntity.cardLimit
        if (amountValue < 0)
            throw Exception("Can't add negative value")
        else if (amountValue > userLimit)
            throw NotEnoughMoneyException("Not enough money!")
        else {
            singleCard.cardLimit += amountValue
            userLimit -= amountValue
        }
        singleUser.currentLimit = userLimit
        userRepo.save(singleUser)
        return cardRepo.save(singleCard)
    }

    fun updateCardName(userId: Long, cardId: Long, cardEntity: CardEntity): CardEntity {
        val singleCard = cardRepo
            .findById(cardId)
            .orElseThrow { CardNotFoundException("Card with id $cardId not found.") }
        val userWallets = userRepo.findById(userId).get().cards
        val cardName = userWallets.map { it.cardName }
        when (
            val cardTitle = cardEntity.cardName
        ) {
            in cardName -> throw Exception("$cardTitle name already exist!")
            else -> singleCard.cardName = cardTitle
        }
        return cardRepo.save(singleCard)
    }

    fun deleteCardById(id: Long) {
        val card = cardRepo.findById(id).get()
        return cardRepo.delete(card)
    }

    fun deleteUserCards(id: Long) {
        val cards = userRepo.findById(id).get().cards
        for (card in cards) {
            if (cards.isEmpty()) {
                throw Exception("No cards for user $id exists!")
            }
            deleteCardById(card.id)
        }
    }
}