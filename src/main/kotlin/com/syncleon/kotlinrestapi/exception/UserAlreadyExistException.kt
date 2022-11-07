package com.syncleon.kotlinrestapi.exception

class UserAlreadyExistException(message: String?) : Exception(message) {
    override val message: String?
        get() = super.message
}