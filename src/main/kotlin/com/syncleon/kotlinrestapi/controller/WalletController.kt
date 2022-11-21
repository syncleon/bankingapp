package com.syncleon.kotlinrestapi.controller

import com.syncleon.kotlinrestapi.entity.WalletEntity
import com.syncleon.kotlinrestapi.service.WalletsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
            ResponseEntity.ok("Wallet: \"${wallet.title}\", added to user: \"$userId\"")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/all")
    fun getAllWallets(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(walletService.getWallets())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("/wallet/user={userId}wallet={walletId}/title")
    fun updateWalletTitle(
        @PathVariable userId:Long,
        @PathVariable walletId: Long,
        @RequestBody wallet: WalletEntity
    ): ResponseEntity<Any> {
        return try {
            walletService.updateWalletTitle(userId, walletId, wallet)
            ResponseEntity.ok(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("/{id}/balance/add")
    fun addMoneyOnWallet(
        @PathVariable id: Long,
        @RequestBody wallet: WalletEntity
    ): ResponseEntity<Any> {
        return try {
            walletService.addMoney(id, wallet)
            ResponseEntity.ok(HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/wallet/{id}")
    fun deleteWallet(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        return try {
            walletService.deleteWalletById(id)
            ResponseEntity.ok("Wallet deleted.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/user/{id}/delete/wallets")
    fun deleteUserWallets(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        return try {
            walletService.deleteWalletsUserId(id)
            ResponseEntity.ok("Wallet deleted.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}