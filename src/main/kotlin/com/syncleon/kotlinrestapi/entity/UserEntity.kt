package com.syncleon.kotlinrestapi.entity

import javax.persistence.*


@Entity
@Table(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0L,
    @Column(name = "username")
    var username:String = "",
    @Column(name = "password")
    var password:String = "",
    @Column(name = "created_at")
    var createdAt: String = "",
    @Column(name = "money_balance")
    var moneyBalance: Double = 0.0,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var wallets: List<WalletEntity> = emptyList()
)