package com.syncleon.kotlinrestapi.entity

import javax.persistence.*

@Entity
@Table(name = "transactions")
class TransactionEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    val id: Long = 0L,

    @Column(name = "amount")
    val amount: Double = 0.0,

    @ManyToOne
    @JoinColumn(name = "sender_card_id")
    var senderId: CardEntity?,

    @ManyToOne
    @JoinColumn(name = "receiver_card_id")
    var receiverId: CardEntity?,

    @Column(name = "transfer_date")
    var transferDate: String = "",
)