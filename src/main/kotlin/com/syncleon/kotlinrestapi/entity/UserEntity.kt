package com.syncleon.kotlinrestapi.entity

import javax.persistence.*


@Entity
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0L,
    var username:String = "",
    var password:String = "",
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var wallets: List<WalletEntity>
)