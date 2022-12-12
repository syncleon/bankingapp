package com.syncleon.kotlinrestapi.repository

import com.syncleon.kotlinrestapi.entity.CardEntity
import org.springframework.data.repository.CrudRepository

interface CardRepo : CrudRepository<CardEntity, Long> {
}