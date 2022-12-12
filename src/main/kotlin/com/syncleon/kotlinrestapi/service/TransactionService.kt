package com.syncleon.kotlinrestapi.service

import com.syncleon.kotlinrestapi.entity.TransactionEntity
import com.syncleon.kotlinrestapi.exception.NotEnoughMoneyException
import com.syncleon.kotlinrestapi.repository.MoneyTransferRepo
import com.syncleon.kotlinrestapi.repository.CardRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionService (
    @Autowired
    val cardRepo: CardRepo,
    @Autowired
    val moneyTransferRepo: MoneyTransferRepo
) {

    fun performTransaction(
        senderId: Long, receiverId: Long, transactionEntity: TransactionEntity
    ): TransactionEntity? {
        val sender = cardRepo.findById(senderId).get()
        val receiver = cardRepo.findById(receiverId).get()

        transactionEntity.senderId = sender
        transactionEntity.receiverId = receiver

        val senderValue = sender.cardLimit
        val amountValue = transactionEntity.amount

        if (amountValue < 0)
            throw Exception("Cant amount negative value")
        else if (amountValue > senderValue)
            throw NotEnoughMoneyException("Not enough money!")
        else receiver.cardLimit += amountValue

        sender.cardLimit -= amountValue
        cardRepo.save(sender)
        cardRepo.save(receiver)
        return moneyTransferRepo.save(transactionEntity)
    }
}