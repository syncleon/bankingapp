package com.syncleon.kotlinrestapi.service

import com.syncleon.kotlinrestapi.entity.UserEntity
import com.syncleon.kotlinrestapi.exception.UserAlreadyExistException
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.model.User
import com.syncleon.kotlinrestapi.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service


@Service
class UserService(
    @Autowired
    private val userRepo: UserRepo
    ) {

    fun create(user: UserEntity): UserEntity {
        user.createdAt = System.currentTimeMillis().toString()
        try {
            if (userRepo.findByUsername(user.username) != null) {
                throw UserAlreadyExistException("User already exists")
            }
        } catch (e: EmptyResultDataAccessException) {
            e.message
        }
        return userRepo.save(user)
    }

    fun updateCurrentLimit(id: Long, user: UserEntity): UserEntity {
        val singleUser = userRepo
            .findById(id)
            .orElseThrow {
                UserNotFoundException("User with id: $id not found")
            }
        val currentLimit = user.currentLimit
        when {
            currentLimit < 0 -> throw Exception("Unable to amount negative value.")
            else -> singleUser.currentLimit += currentLimit
        }
        return userRepo.save(singleUser)
    }

    fun getUsers(): MutableList<User> {
        return User.toModelGetAll(userRepo.findAll())
    }

    fun getUser(id: Long): User {
        val user = userRepo.findById(id)
        return if (user.isEmpty) {
            throw UserNotFoundException("User not found.")
        } else User.toModel(user.get())
    }

    fun deleteUser(id: Long) {
        val user = userRepo.findById(id)
        return if (user.isEmpty) {
            throw UserNotFoundException("User not found.")
        } else userRepo.delete(user.get())
    }
}