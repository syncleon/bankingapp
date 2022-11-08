package com.syncleon.kotlinrestapi.repository

import com.syncleon.kotlinrestapi.entity.WalletEntity
import org.springframework.data.repository.CrudRepository

interface WalletRepo : CrudRepository<WalletEntity, Long> {
}