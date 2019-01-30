package com.danieldisu.hnnotify.application

import com.danieldisu.hnnotify.domain.data.Story
import com.danieldisu.hnnotify.framework.errors.ErrorHandler
import com.danieldisu.hnnotify.framework.reactor.onErrorContinue
import com.danieldisu.hnnotify.framework.repositories.NewStoriesRepository
import com.danieldisu.hnnotify.framework.repositories.StoryDetailRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalTime

@Service
class FetchLatestPosts(
    private val newStoriesRepository: NewStoriesRepository,
    private val storyDetailRepository: StoryDetailRepository,
    private val errorHandler: ErrorHandler
) {

    @Value("\${hnnotify.maxStoriesToFetch}")
    var maxStoriesToFetch: Int = 0

    fun execute(): Mono<List<Story>> {
        return newStoriesRepository.get()
            .map { limitTheNumberOfStories(it) }
            .onErrorMap { ErrorFetchingNewStories(LocalTime.now(), it) }
            .doOnError(errorHandler::handle)
            .flatMapMany { storyIds -> getStoriesAsFlux(storyIds) }
            .collectList()
    }

    private fun limitTheNumberOfStories(it: List<String>): List<String> {
        return if (maxStoriesToFetch > 0) {
            it.take(maxStoriesToFetch)
        } else {
            it
        }
    }

    private fun getStoriesAsFlux(storyIds: List<String>): Flux<Story> {
        return Flux.merge(storyIds.map { id ->
            storyDetailRepository.get(id)
                .onErrorMap { ErrorFetchingStory(id, it) }
                .doOnError(errorHandler::handle)
                .onErrorContinue()
        })
    }

}

data class ErrorFetchingNewStories(val time: LocalTime, override val cause: Throwable) : Throwable()
data class ErrorFetchingStory(val storyId: String, override val cause: Throwable) : Throwable()