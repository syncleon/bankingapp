package com.syncleon.kotlinrestapi.service

import com.syncleon.kotlinrestapi.entity.UserEntity
import com.syncleon.kotlinrestapi.entity.WalletEntity
import com.syncleon.kotlinrestapi.repository.UserRepo
import com.syncleon.kotlinrestapi.repository.WalletRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WalletsService(
    @Autowired
    private val walletRepo: WalletRepo,
    @Autowired
    private val userRepo:UserRepo
) {
    fun addWallet(wallet: WalletEntity, userId: Long): WalletEntity {
        val user: UserEntity = userRepo.findById(userId).get()
        wallet.user = user
        return walletRepo.save(wallet)
    }

    fun amountWallet() {}
}