package com.syncleon.kotlinrestapi.controller

import com.syncleon.kotlinrestapi.entity.UserEntity
import com.syncleon.kotlinrestapi.exception.UserAlreadyExistException
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.repository.UserRepo
import com.syncleon.kotlinrestapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
            ResponseEntity.ok(userService.getAll())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping
    fun getOneUser(@RequestParam id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.getOne(id))
        } catch (e: UserNotFoundException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            userService.deleteUser(id)
            ResponseEntity.ok("User with id=$id deleted.")
        } catch (e: UserNotFoundException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}