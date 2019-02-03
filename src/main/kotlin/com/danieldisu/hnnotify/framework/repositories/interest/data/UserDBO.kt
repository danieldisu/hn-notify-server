package com.danieldisu.hnnotify.framework.repositories.interest.data

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class UserDBO(
    @Id
    @Column
    val id: String = ""
)