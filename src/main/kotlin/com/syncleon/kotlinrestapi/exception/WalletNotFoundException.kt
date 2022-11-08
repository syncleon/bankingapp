package com.syncleon.kotlinrestapi.exception


class WalletNotFoundException(message: String?): Exception(message) {
    override val message: String?
        get() = super.message
}