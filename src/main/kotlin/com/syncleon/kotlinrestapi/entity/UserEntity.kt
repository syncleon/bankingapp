package com.syncleon.kotlinrestapi.entity

import javax.persistence.*


@Entity
@Table(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var username:String = "",
    var password:String = "",
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var wallets: List<WalletEntity> = emptyList()
)