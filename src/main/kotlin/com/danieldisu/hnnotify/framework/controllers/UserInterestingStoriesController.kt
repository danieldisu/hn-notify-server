package com.danieldisu.hnnotify.framework.controllers

import com.danieldisu.hnnotify.application.GetAllInterestingStories
import com.danieldisu.hnnotify.framework.extensions.toStoryDto
import com.danieldisu.hnnotify.framework.hn.data.StoryDTO
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
    fun get(@PathVariable userId: String): Mono<List<StoryDTO>> {
        return getAllInterestingStories.execute(userId)
            .toStoryDto()
    }

}
