package com.syncleon.kotlinrestapi.exception

class UserNotFoundException(message: String?): Exception(message) {
    override val message: String?
        get() = super.message
}