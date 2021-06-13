package com.danieldisu.hnnotify.framework.repositories.data

import org.hibernate.annotations.CreationTimestamp
import java.sql.Date
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
    @GeneratedValue
    @Id
    @Column
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "userId")
    val user: UserDBO = UserDBO(),

    val interestName: String = "",

    @CreationTimestamp
    val addedOn: Date = Date(0L)
)
