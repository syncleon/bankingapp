package com.syncleon.kotlinrestapi.controller

import com.syncleon.kotlinrestapi.entity.MoneyTransferEntity
import com.syncleon.kotlinrestapi.entity.WalletEntity
import com.syncleon.kotlinrestapi.service.MoneyTransferService
import com.syncleon.kotlinrestapi.service.WalletsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wallets")
class WalletController(
    @Autowired
    val walletService:WalletsService,
    @Autowired
    val moneyTransferService: MoneyTransferService
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

    @PostMapping("/transfer")
    fun moneyTransfer(
        @RequestBody moneyTransfer: MoneyTransferEntity,
        @RequestParam senderId: Long,
        @RequestParam receiverId: Long
    ): ResponseEntity<Any> {
        return try {
            moneyTransferService.transferMoney(senderId, receiverId, moneyTransfer)
            ResponseEntity.ok("received!")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/getAll")
    fun getAllWallets(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(walletService.getWallets())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("/user{userId}wallet{walletId}/title")
    fun updateTitle(
        @PathVariable userId: Long,
        @PathVariable walletId: Long,
        @RequestBody wallet: WalletEntity
    ): ResponseEntity<Any> {
        return try {
            walletService.updateWalletTitle(userId, walletId, wallet)
            ResponseEntity.ok("Title updated!")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("/wallet{id}user{userId}/addMoney")
    fun addMoney(
        @PathVariable id: Long,
        @PathVariable userId: Long,
        @RequestBody wallet: WalletEntity
    ): ResponseEntity<Any> {
        return try {
            walletService.addMoney(id, userId, wallet)
            ResponseEntity.ok("Added!")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/deleteWallet{id}")
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

    @DeleteMapping("/user{id}/deleteWallets")
    fun deleteUserWallets(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        return try {
            walletService.deleteWalletsUserId(id)
            ResponseEntity.ok("Wallets deleted.")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}