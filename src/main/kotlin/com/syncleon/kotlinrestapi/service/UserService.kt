package com.syncleon.kotlinrestapi.service

import com.syncleon.kotlinrestapi.entity.UserEntity
import com.syncleon.kotlinrestapi.exception.UserAlreadyExistException
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.model.User
import com.syncleon.kotlinrestapi.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.lang.NonNull
import org.springframework.stereotype.Service


@Service
class UserService(
    @Autowired
    private val userRepo: UserRepo
    ) {
    fun registration(user: UserEntity): UserEntity {
        try {
            if (userRepo.findByUsername(user.username) != null) {
                throw UserAlreadyExistException("User already exists")
            }
        } catch (e: EmptyResultDataAccessException) {
            e.message
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
        return User.toModelGetAll(userRepo.findAll())
    }
}