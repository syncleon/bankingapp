package com.syncleon.kotlinrestapi.repository

import com.syncleon.kotlinrestapi.entity.MoneyTransferEntity
import org.springframework.data.repository.CrudRepository

interface MoneyTransferRepo : CrudRepository<MoneyTransferEntity, Long> {
}