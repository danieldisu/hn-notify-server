package com.danieldisu.hnnotify.framework.repositories.data

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "hn_user")
data class UserDBO(
        @Id
        @Column
        val id: String = ""
)
