package com.syncleon.kotlinrestapi.controller

import com.syncleon.kotlinrestapi.entity.WalletEntity
import com.syncleon.kotlinrestapi.exception.UserAlreadyExistException
import com.syncleon.kotlinrestapi.service.WalletsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallets")
class WalletController(
    @Autowired
    private val walletService:WalletsService
) {
    @PostMapping
    fun addWallet(
        @RequestBody wallet: WalletEntity,
        @RequestParam userId: Long
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(walletService.addWallet(wallet, userId))
        } catch (e: UserAlreadyExistException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}