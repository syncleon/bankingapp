package com.syncleon.kotlinrestapi.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class WalletEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0L,
    var title:String = "",
    var amount: Double = 0.0,
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user:UserEntity?
)