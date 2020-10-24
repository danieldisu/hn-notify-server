package com.danieldisu.hnnotify.framework.clients

import com.danieldisu.hnnotify.framework.hn.HackerNewsApi
import com.danieldisu.hnnotify.framework.hn.data.StoryDTO
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class StoryDetailClient(private val hackerNewsApi: HackerNewsApi) {

  fun get(storyId: String): Mono<StoryDTO> {
    return hackerNewsApi.getStoryDetail(storyId)
        .map { StoryDTO(it.id, it.title) }
  }

}
