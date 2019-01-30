package com.danieldisu.hnnotify.framework.repositories

import com.danieldisu.hnnotify.domain.data.Story
import com.danieldisu.hnnotify.framework.hn.HackerNewsApiClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class StoryDetailRepository(private val hackerNewsApiClient: HackerNewsApiClient) {

    fun get(storyId: String): Mono<Story> {
        return hackerNewsApiClient.getStoryDetail(storyId)
            .map { Story(it.id, it.title) }
    }

}