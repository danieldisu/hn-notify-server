package com.danieldisu.hnnotify.framework.clients

import com.danieldisu.hnnotify.framework.hn.HackerNewsApi
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class NewStoriesClient(private val hackerNewsApi: HackerNewsApi) {

    fun get(): Mono<List<String>> {
        return hackerNewsApi.getNewStories()
    }

}