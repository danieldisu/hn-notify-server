package com.danieldisu.hnnotify.framework.repositories.interest.data

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UserDBO(
    @Id
    @Column(name = "userId")
    val id: String = ""
)