package com.danieldisu.hnnotify.application

import com.danieldisu.hnnotify.domain.data.Story
import com.danieldisu.hnnotify.framework.repositories.story.StoryRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Component
class GetAllStories(
    private val storyRepository: StoryRepository
) {

    fun execute(): Mono<List<Story>> {
        return storyRepository.findAll()
            .map { it.toStory() }
            .toMono()
    }

}
