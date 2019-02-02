package com.danieldisu.hnnotify.framework.repositories.story.data

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class StoryDBO(
    @Id val id: String = "",
    val title: String = ""
)