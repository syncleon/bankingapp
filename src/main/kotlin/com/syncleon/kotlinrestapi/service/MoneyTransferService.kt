package com.syncleon.kotlinrestapi.service

import com.syncleon.kotlinrestapi.entity.MoneyTransferEntity
import com.syncleon.kotlinrestapi.exception.NotEnoughMoneyException
import com.syncleon.kotlinrestapi.exception.WalletNotFoundException
import com.syncleon.kotlinrestapi.repository.MoneyTransferRepo
import com.syncleon.kotlinrestapi.repository.WalletRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MoneyTransferService (
    @Autowired
    val walletRepo: WalletRepo,
    @Autowired
    val moneyTransferRepo: MoneyTransferRepo
) {
    fun transferMoney(
        senderId: Long,
        receiverId: Long,
        moneyTransfer: MoneyTransferEntity
    ): MoneyTransferEntity? {
        val sender = walletRepo.findById(senderId).get()
        val receiver = walletRepo.findById(receiverId).get()
        moneyTransfer.senderId = sender
        moneyTransfer.receiverId = receiver
        val senderValue = sender.amount
        val amount = moneyTransfer.amount
        if (amount < 0)
            throw Exception("Cant transfer negative value")
        else if (amount > senderValue)
            throw NotEnoughMoneyException("Not enough money!")
        else receiver.amount += amount
        sender.amount -= amount
        walletRepo.save(sender)
        walletRepo.save(receiver)
        return moneyTransferRepo.save(moneyTransfer)
    }
}