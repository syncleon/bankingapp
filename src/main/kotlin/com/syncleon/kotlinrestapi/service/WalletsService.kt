package com.syncleon.kotlinrestapi.service

import com.syncleon.kotlinrestapi.entity.MoneyTransferEntity
import com.syncleon.kotlinrestapi.entity.WalletEntity
import com.syncleon.kotlinrestapi.exception.NotEnoughMoneyException
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.exception.WalletNotFoundException
import com.syncleon.kotlinrestapi.model.Wallet
import com.syncleon.kotlinrestapi.repository.MoneyTransferRepo
import com.syncleon.kotlinrestapi.repository.UserRepo
import com.syncleon.kotlinrestapi.repository.WalletRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Service
class WalletsService(

    @Autowired
    val walletRepo: WalletRepo,
    @Autowired
    val userRepo:UserRepo,

) {
    fun addWallet(wallet: WalletEntity, userId: Long): WalletEntity {
        val user = userRepo.findById(userId)
        if (user.isEmpty) {
            throw UserNotFoundException("Wallet cant be added to not created user")
        }
        val userWallets = userRepo.findById(userId).get().wallets
        val walletTitles = userWallets.map { it.title }
        when (
            val walletTitle = wallet.title
        ) {
            in walletTitles -> throw Exception("$walletTitle name already exist, select another name")
        }
        wallet.user = user.get()
        wallet.createdAt = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
        return walletRepo.save(wallet)
    }

    fun getWallets(): MutableList<Wallet> {
        return Wallet.toModelGetAll(walletRepo.findAll())
    }

    fun addMoney(walletId: Long, userId: Long, wallet: WalletEntity): WalletEntity? {
        val singleWallet = walletRepo
            .findById(walletId)
            .orElseThrow {
                WalletNotFoundException("Wallet with id {$walletId} not found.") }
        val singleUser = userRepo
            .findById(userId)
            .orElseThrow {
                UserNotFoundException("User with id: $userId not found") }
        var userBalance = singleUser.moneyBalance
        val amount = wallet.amount
        if (amount < 0)
            throw Exception("Cant add negative value")
        else if (amount > userBalance)
            throw NotEnoughMoneyException("Not enough money! ")
        else {
            singleWallet.amount.plus(amount)
            userBalance -= amount
        }
        singleUser.moneyBalance = userBalance
        userRepo.save(singleUser)
        return walletRepo.save(singleWallet)}


    fun updateWalletTitle(userId: Long, walletId: Long, wallet: WalletEntity): WalletEntity {
        val singleWallet = walletRepo
            .findById(walletId)
            .orElseThrow { WalletNotFoundException("Wallet with id {$walletId} not found.") }
        val userWallets = userRepo.findById(userId).get().wallets
        val walletTitles = userWallets.map { it.title }
        when (
            val walletTitle = wallet.title
        ) {
            in walletTitles -> throw Exception("$walletTitle name already exist")
            else -> singleWallet.title = walletTitle
        }
        return walletRepo.save(singleWallet)
    }

    fun deleteWalletById(id: Long) {
        val wallet = walletRepo.findById(id).get()
        return walletRepo.delete(wallet)
    }

    fun deleteWalletsUserId(id: Long) {
        val wallets = userRepo.findById(id).get().wallets
        for (wal in wallets) {
            if (wallets.isEmpty()) {
                throw Exception("No wallets for user $id exists!")
            }
            deleteWalletById(wal.id)
        }
    }
}