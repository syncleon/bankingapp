package com.syncleon.kotlinrestapi.entity

import javax.persistence.*

@Entity
@Table(name = "cards")
class CardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0L,

    @Column(name = "card_name")
    var cardName: String = "",

    @Column(name = "card_limit")
    var cardLimit: Double = 0.0,

    @Column(name = "created_at")
    var createdAt: String = "",

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user:UserEntity?,

    @OneToMany(mappedBy = "senderId", fetch = FetchType.EAGER)
    var senders: List<TransactionEntity> = emptyList(),

    @OneToMany(mappedBy = "receiverId", fetch = FetchType.EAGER)
    var receivers: List<TransactionEntity> = emptyList()
)