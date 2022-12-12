package com.syncleon.kotlinrestapi.entity

import javax.persistence.*

@Entity
@Table(name = "wallets")
class WalletEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0L,
    @Column(name = "title")
    var title: String = "",
    @Column(name = "amount")
    var amount: Double = 0.0,
    @Column(name = "description")
    var description: String = "",
    @Column(name = "created_at")
    var createdAt: String = "",
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user:UserEntity?,

    @OneToMany(mappedBy = "senderId", fetch = FetchType.EAGER)
    var senders: List<MoneyTransferEntity> = emptyList(),
    @OneToMany(mappedBy = "receiverId", fetch = FetchType.EAGER)
    var receivers: List<MoneyTransferEntity> = emptyList()
)