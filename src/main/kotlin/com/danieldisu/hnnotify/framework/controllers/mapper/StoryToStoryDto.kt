package com.danieldisu.hnnotify.framework.controllers.mapper

import com.danieldisu.hnnotify.domain.data.Story
import com.danieldisu.hnnotify.framework.hn.data.StoryDTO

fun List<Story>.toStoryDto(): List<StoryDTO> {
    return map { story ->
        StoryDTO.fromStory(story)
    }
}
