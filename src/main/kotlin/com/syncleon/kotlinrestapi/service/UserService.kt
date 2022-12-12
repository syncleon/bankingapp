package com.syncleon.kotlinrestapi.service

import com.syncleon.kotlinrestapi.entity.UserEntity
import com.syncleon.kotlinrestapi.exception.UserAlreadyExistException
import com.syncleon.kotlinrestapi.exception.UserNotFoundException
import com.syncleon.kotlinrestapi.model.User
import com.syncleon.kotlinrestapi.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@Service
class UserService(
    @Autowired
    private val userRepo: UserRepo
    ) {

    fun create(user: UserEntity): UserEntity {
        user.createdAt = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
        try {
            if (userRepo.findByUsername(user.username) != null) {
                throw UserAlreadyExistException("User already exists")
            }
        } catch (e: EmptyResultDataAccessException) {
            e.message
        }
        return userRepo.save(user)
    }

    fun updateBalance(id: Long, user: UserEntity): UserEntity {
        val singleUser = userRepo
            .findById(id)
            .orElseThrow {
                UserNotFoundException("User with id: $id not found")
            }
        val userBalance = user.moneyBalance
        when {
            userBalance < 0 -> throw Exception("Unable to amount negative value.")
            else -> singleUser.moneyBalance += userBalance
        }
        return userRepo.save(singleUser)
    }

    fun getUsers(): MutableList<User> {
        return User.toModelGetAll(userRepo.findAll())
    }

    fun getUser(id: Long): User {
        val user = userRepo.findById(id)
        return if (user.isEmpty) {
            throw UserNotFoundException("User not found")
        } else User.toModel(user.get())
    }

    fun deleteUser(id: Long) {
        val user = userRepo.findById(id)
        return if (user.isEmpty) {
            throw UserNotFoundException("User not found")
        } else userRepo.delete(user.get())
    }
}