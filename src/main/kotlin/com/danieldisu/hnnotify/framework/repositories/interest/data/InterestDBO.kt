package com.danieldisu.hnnotify.framework.repositories.interest.data

import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.CreationTimestamp
import java.sql.Date
import javax.persistence.*

@Entity
data class InterestDBO(
    @GeneratedValue
    @Id
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name="userId")
    @Cascade(CascadeType.ALL)
    val user: UserDBO = UserDBO(),

    val interestName: String = "",

    @CreationTimestamp
    val addedOn: Date = Date(0L)
)