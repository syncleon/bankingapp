package com.syncleon.kotlinrestapi.exception

class NotEnoughMoneyException(message: String?): Exception(message) {
    override val message: String?
        get() = super.message
}