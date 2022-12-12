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

    @GetMapping("/all")
    fun getUsers(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getUsers())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping
    fun getUser(
        @RequestParam userId: Long
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getUser(userId))
        } catch (e: UserNotFoundException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PostMapping("/create")
    fun create(
        @RequestBody user: UserEntity
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.create(user))
        } catch (e: UserAlreadyExistException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @PutMapping("/updateCurrentLimit")
    fun updateCurrentLimit(
        @RequestParam userId: Long,
        @RequestBody user: UserEntity
    ): ResponseEntity<Any> {
        return try {
            userService.updateCurrentLimit(userId, user)
            ResponseEntity.accepted().body("Current limit updated!")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/delete")
    fun deleteUserById(
        @RequestParam userId: Long
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.deleteUser(userId))
        } catch (e: UserNotFoundException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}