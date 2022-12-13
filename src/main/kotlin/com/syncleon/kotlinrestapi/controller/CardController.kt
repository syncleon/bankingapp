package com.syncleon.kotlinrestapi.controller

import com.syncleon.kotlinrestapi.entity.TransactionEntity
import com.syncleon.kotlinrestapi.entity.CardEntity
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.service.TransactionService
import com.syncleon.kotlinrestapi.service.CardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cards")
class CardController(
    @Autowired
    val cardService: CardService,
    @Autowired
    val transactionService: TransactionService
) {

    @GetMapping("/all")
    fun getAllCards(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(cardService.getAllCards())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping
    fun getCardById(
        @RequestParam cardId: Long
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(cardService.getCard(cardId))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }


    @PostMapping("/add")
    fun addCard(
        @RequestBody cardEntity: CardEntity,
        @RequestParam userId: Long
    ): ResponseEntity<Any> {
        return try {
            cardService.addCard(cardEntity, userId)
            ResponseEntity.ok("Card: \"${cardEntity.cardName}\", added to user: \"$userId\"")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/transaction")
    fun transaction(
        @RequestBody transactionEntity: TransactionEntity,
        @RequestParam senderId: Long,
        @RequestParam receiverId: Long
    ): ResponseEntity<Any> {
        return try {
            transactionService.performTransaction(senderId, receiverId, transactionEntity)
            ResponseEntity.ok("Received ${transactionEntity.amount} \'$ from $senderId to $receiverId")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("/updateCardName")
    fun updateCardName(
        @RequestParam userId: Long,
        @RequestParam cardId: Long,
        @RequestBody cardEntity: CardEntity
    ): ResponseEntity<Any> {
        return try {
            cardService.updateCardName(userId, cardId, cardEntity)
            ResponseEntity.ok("Card name updated!")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("/amount")
    fun amountCard(
        @RequestParam cardId: Long,
        @RequestParam userId: Long,
        @RequestBody cardEntity: CardEntity
    ): ResponseEntity<Any> {
        return try {
            cardService.amountCard(cardId, userId, cardEntity)
            ResponseEntity.ok("Card limit updated!")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/delete")
    fun deleteCardById(
        @RequestParam cardId: Long
    ): ResponseEntity<Any> {
        return try {
            cardService.deleteCardById(cardId)
            ResponseEntity.ok("Card $cardId deleted.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/deleteCards")
    fun deleteUserCards(
        @RequestParam userId: Long
    ): ResponseEntity<Any> {
        return try {
            cardService.deleteUserCards(userId)
            ResponseEntity.ok("All cards for user $userId deleted.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}