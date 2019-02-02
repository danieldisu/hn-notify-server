package com.danieldisu.hnnotify.application

import com.danieldisu.hnnotify.domain.data.Story
import com.danieldisu.hnnotify.framework.clients.NewStoriesClient
import com.danieldisu.hnnotify.framework.clients.StoryDetailClient
import com.danieldisu.hnnotify.framework.errors.ErrorHandler
import com.danieldisu.hnnotify.framework.reactor.onErrorContinue
import com.danieldisu.hnnotify.framework.repositories.story.StoryRepository
import com.danieldisu.hnnotify.framework.repositories.story.data.StoryDBO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalTime

@Service
class FetchLatestNewStories(
    private val newStoriesClient: NewStoriesClient,
    private val storyDetailClient: StoryDetailClient,
    private val storyRepository: StoryRepository,
    private val errorHandler: ErrorHandler
) {

    @Value("\${hnnotify.maxStoriesToFetch}")
    var maxStoriesToFetch: Int = 0

    fun execute(): Mono<List<Story>> {
        return newStoriesClient.get()
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
            storyDetailClient.get(id)
                .flatMap { saveInDatabase(it) }
                .onErrorMap { ErrorFetchingStory(id, it) }
                .doOnError(errorHandler::handle)
                .onErrorContinue()
        })
    }

    private fun saveInDatabase(story: Story): Mono<Story> {
        return Mono.fromCallable {
            storyRepository.save(
                StoryDBO(
                    id = story.id,
                    title = story.title
                )
            )
            story
        }
    }


}

data class ErrorFetchingNewStories(val time: LocalTime, override val cause: Throwable) : Throwable()
data class ErrorFetchingStory(val storyId: String, override val cause: Throwable) : Throwable()