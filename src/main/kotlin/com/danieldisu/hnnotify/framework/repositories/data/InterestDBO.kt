package com.danieldisu.hnnotify.framework.repositories.data

import org.hibernate.annotations.CreationTimestamp
import java.sql.Date
import java.util.*
import javax.persistence.*

const val uniqueInterestNameConstraint = "uniqueInterestNameConstraint"

@Entity
@Table(
    name = "interest",
    uniqueConstraints = [
        UniqueConstraint(
            name = uniqueInterestNameConstraint,
            columnNames = ["userId", "interestName"]
        )
    ]
)
data class InterestDBO(
    @Id
    @Column
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne
    @JoinColumn(name = "userId")
    val user: UserDBO,

    val interestName: String,

    val interestKeywords: String,

    @CreationTimestamp
    val addedOn: Date = Date(0L)
)
