package com.syncleon.kotlinrestapi.entity

import javax.persistence.*

@Entity
@Table(name = "money_transfer")
class MoneyTransferEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    val id: Long = 0L,

    @Column(name = "amount")
    val amount: Double = 0.0,

    @ManyToOne
    @JoinColumn(name = "sender_id")
    var senderId: WalletEntity?,

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    var receiverId: WalletEntity?,

    @Column(name = "transfer_date")
    var transferDate: String = "",
)