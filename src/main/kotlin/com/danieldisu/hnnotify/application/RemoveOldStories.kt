package com.danieldisu.hnnotify.application

import com.danieldisu.hnnotify.framework.repositories.story.StoryRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RemoveOldStories(
    private val storyRepository: StoryRepository
) {

    @Value("\${hnnotify.cleanup.targetNumberOfRows}")
    private var targetNumberOfRows: Int = 0

    @Transactional
    operator fun invoke(): Int {
        val numberOfRows = storyRepository.count().toInt()

        if (numberOfRows >= targetNumberOfRows) {
            val numberOfElementsToRemove = numberOfRows - targetNumberOfRows
            return removeOlderStories(numberOfElementsToRemove)
        }

        return 0
    }

    private fun removeOlderStories(numberOfElementsToRemove: Int): Int {
        val olderStories =
            storyRepository.findAllByOrderByIdAsc(PageRequest.of(0, numberOfElementsToRemove))

        return storyRepository.deleteAllByIdIn(olderStories.map { it.id })
    }

}
