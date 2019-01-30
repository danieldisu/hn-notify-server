package com.danieldisu.hnnotify.framework.controllers

import com.danieldisu.hnnotify.application.FetchLatestPosts
import com.danieldisu.hnnotify.domain.data.Story
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/stories")
class StoriesController(
    private val fetchLatestPosts: FetchLatestPosts
) {

    @PostMapping("/fetch", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fetch(): Mono<List<Story>> {
        return fetchLatestPosts.execute()
    }

}