package com.danieldisu.hnnotify.framework.controllers

import com.danieldisu.hnnotify.application.GetAllInterestingStories
import com.danieldisu.hnnotify.domain.data.Story
import com.danieldisu.hnnotify.framework.controllers.mapper.toStoryDto
import com.danieldisu.hnnotify.framework.controllers.response.GetUserInterestingStoriesResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/user/{userId}/stories")
class UserInterestingStoriesController(
    private val getAllInterestingStories: GetAllInterestingStories
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@PathVariable userId: String): Mono<GetUserInterestingStoriesResponse> {
        return getAllInterestingStories.execute(userId)
            .map { it.toResponse() }
    }

    private fun List<Story>.toResponse() =
        GetUserInterestingStoriesResponse(stories = this.toStoryDto())
}

