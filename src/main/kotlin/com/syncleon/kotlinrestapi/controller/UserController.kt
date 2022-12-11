package com.syncleon.kotlinrestapi.controller

import com.syncleon.kotlinrestapi.entity.UserEntity
import com.syncleon.kotlinrestapi.exception.UserAlreadyExistException
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    @Autowired
    private val userService: UserService
    ) {

    @PostMapping("/add")
    fun create(
        @RequestBody
        user: UserEntity
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.create(user))
        } catch (e: UserAlreadyExistException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("user{id}/amount")
    fun updateBalance(
        @PathVariable
        id: Long,
        @RequestBody
        user: UserEntity
    ): ResponseEntity<Any> {
        return try {
            userService.updateBalance(id, user)
            ResponseEntity.accepted().body("Updated!")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/getAll")
    fun getUsers(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getUsers())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/user{id}")
    fun getUser(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getUser(id))
        } catch (e: UserNotFoundException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/delete{id}")
    fun deleteUserById(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.deleteUser(id))
        } catch (e: UserNotFoundException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}