package com.syncleon.kotlinrestapi.exception


class CardNotFoundException(message: String?): Exception(message) {
    override val message: String?
        get() = super.message
}