package com.danieldisu.hnnotify.framework.clients

import com.danieldisu.hnnotify.domain.data.Story
import com.danieldisu.hnnotify.framework.hn.HackerNewsApi
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class StoryDetailClient(private val hackerNewsApi: HackerNewsApi) {

    fun get(storyId: String): Mono<Story> {
        return hackerNewsApi.getStoryDetail(storyId)
            .map { Story(it.id, it.title) }
    }

}