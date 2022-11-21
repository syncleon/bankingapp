package com.syncleon.kotlinrestapi.service

import com.syncleon.kotlinrestapi.entity.WalletEntity
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.exception.WalletNotFoundException
import com.syncleon.kotlinrestapi.model.Wallet
import com.syncleon.kotlinrestapi.repository.UserRepo
import com.syncleon.kotlinrestapi.repository.WalletRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WalletsService(
    @Autowired
    val walletRepo: WalletRepo,
    @Autowired
    val userRepo:UserRepo
) {
    fun addWallet(wallet: WalletEntity, userId: Long): WalletEntity {
        val user = userRepo.findById(userId)
        if (user.isEmpty) {
            throw UserNotFoundException("Wallet cant be added to not created user")
        }
        wallet.user = user.get()
        return walletRepo.save(wallet)
    }

    fun getWallets(): MutableList<Wallet> {
        return Wallet.toModelGetAll(walletRepo.findAll())
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

    fun updateWalletTitle(userId: Long, walletId: Long, wallet: WalletEntity): WalletEntity {
        val singleWallet = walletRepo
            .findById(walletId)
            .orElseThrow { WalletNotFoundException("Wallet with id {$walletId} not found.") }
        val userWallets = userRepo.findById(userId).get().wallets
        val walletTitles = userWallets.map { it.title }
        when (val walletTitle = wallet.title) {
            in walletTitles -> throw Exception("Wallet already exist")
            else -> singleWallet.title = walletTitle
        }
        return walletRepo.save(singleWallet)
    }

    fun addMoney(id: Long, wallet: WalletEntity): WalletEntity {
        val singleWallet = walletRepo
            .findById(id)
            .orElseThrow { WalletNotFoundException("Wallet with id {$id} not found.") }
        val walletBalance = wallet.balance
        when {
            walletBalance < 0 -> throw Exception("Unable to add negative value.")
            else -> singleWallet.balance += walletBalance
        }
        return walletRepo.save(singleWallet)
    }
}