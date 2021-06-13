package com.danieldisu.hnnotify.framework.hn.data

import com.danieldisu.hnnotify.domain.data.Story

data class StoryDTO(
    val id: String,
    val title: String
) {
    fun toStory(): Story {
        return Story(
            id = id,
            title = title
        )
    }

    companion object {
        fun fromStory(story: Story): StoryDTO {
            return StoryDTO(
                id = story.id,
                title = story.title
            )
        }
    }
}
