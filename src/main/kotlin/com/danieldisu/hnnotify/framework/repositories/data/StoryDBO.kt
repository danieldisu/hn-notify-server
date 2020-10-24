package com.danieldisu.hnnotify.framework.repositories.data

import com.danieldisu.hnnotify.domain.data.Story
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "story")
data class StoryDBO(
        @Id val id: String,
        val title: String
) {

  fun toStory(): Story {
    return Story(
            id = id,
            title = title
    )
  }
}
