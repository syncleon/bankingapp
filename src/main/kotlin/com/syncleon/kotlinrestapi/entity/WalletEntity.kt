package com.syncleon.kotlinrestapi.entity

import javax.persistence.*

@Entity
@Table(name = "wallets")
class WalletEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var title: String = "",
    var balance: Double = 0.0,
    var description: String = "",
    var created_at: String = "",
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user:UserEntity?
)