package com.syncleon.kotlinrestapi.controller

import com.syncleon.kotlinrestapi.entity.UserEntity
import com.syncleon.kotlinrestapi.exception.UserAlreadyExistException
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.service.UserService
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    @Autowired
    private val userService: UserService
    ) {

    @PostMapping("/create")
    fun registration(
        @RequestBody
        user: UserEntity
    ): ResponseEntity<Any> {
        return try {
            userService.registration(user)
            ResponseEntity.ok("User created successful!")
        } catch (e: UserAlreadyExistException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/all")
    fun getUsers(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getUsers())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/user/{id}")
    fun getUser(
        @PathVariable id: Long
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getUser(id))
        } catch (e: UserNotFoundException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(
        @PathVariable id: Long,
    ): ResponseEntity<Any> {
        return try {
            userService.deleteUser(id)
            ResponseEntity.ok("User with id=$id deleted.")
        } catch (e: UserNotFoundException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}