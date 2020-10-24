package com.danieldisu.hnnotify.framework.extensions

import com.danieldisu.hnnotify.domain.data.Story
import com.danieldisu.hnnotify.framework.hn.data.StoryDTO
import reactor.core.publisher.Mono

fun Mono<List<Story>>.toStoryDto(): Mono<List<StoryDTO>> {
  return map { storyList ->
    storyList.map { story ->
      StoryDTO.fromStory(story)
    }
  }
}
