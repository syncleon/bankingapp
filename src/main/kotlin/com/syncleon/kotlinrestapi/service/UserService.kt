package com.syncleon.kotlinrestapi.service

import com.syncleon.kotlinrestapi.entity.UserEntity
import com.syncleon.kotlinrestapi.exception.UserAlreadyExistException
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.model.User
import com.syncleon.kotlinrestapi.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UserService(@Autowired private val userRepo: UserRepo) {

    fun registration(user: UserEntity): UserEntity {
        if (userRepo.findByUsername(user.username) != null) {
            throw UserAlreadyExistException("User already exist!")
        }
        return userRepo.save(user)
    }

    fun getOne(id: Long): User {
        val user = userRepo.findById(id)
        return if (user.isEmpty) {
            throw UserNotFoundException("User not found")
        } else User.toModel(user.get())
    }

    fun getAll(): MutableList<User> {
        val user = userRepo.findAll()
        return User.toModelGetAll(user)
    }
}