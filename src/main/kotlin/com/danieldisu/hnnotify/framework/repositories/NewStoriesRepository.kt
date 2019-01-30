package com.danieldisu.hnnotify.framework.repositories

import com.danieldisu.hnnotify.framework.hn.HackerNewsApiClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class NewStoriesRepository(private val hackerNewsApiClient: HackerNewsApiClient) {

    fun get(): Mono<List<String>> {
        return hackerNewsApiClient.getNewStories()
    }

}