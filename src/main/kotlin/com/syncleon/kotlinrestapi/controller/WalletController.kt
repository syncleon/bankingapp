package com.syncleon.kotlinrestapi.controller

import com.syncleon.kotlinrestapi.entity.WalletEntity
import com.syncleon.kotlinrestapi.service.WalletsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wallets")
class WalletController(
    @Autowired
    val walletService:WalletsService
) {
    @PostMapping
    fun addWallet(
        @RequestBody wallet: WalletEntity,
        @RequestParam userId: Long
    ): ResponseEntity<Any> {
        return try {
            walletService.addWallet(wallet, userId)
            ResponseEntity.ok("Wallet title: \"${wallet.title}\", added to userID: \"$userId\"")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/all")
    fun getWallets(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(walletService.getAll())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}