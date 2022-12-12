package com.syncleon.kotlinrestapi.repository

import com.syncleon.kotlinrestapi.entity.TransactionEntity
import org.springframework.data.repository.CrudRepository

interface MoneyTransferRepo : CrudRepository<TransactionEntity, Long> {
}