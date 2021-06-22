package com.danieldisu.hnnotify.framework.jobs

import com.danieldisu.hnnotify.application.RemoveOldStories
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RemoveOlderStoriesJob(private val removeOldStories: RemoveOldStories) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Scheduled(
        fixedDelayString = "\${hnnotify.cleanup.timeBetween}",
        initialDelayString = "\${hnnotify.cleanup.initialDelay}"
    )
    fun fetchLatestNewStories() {
        logger.info("Starting RemoveOlderStoriesJob")
        val storiesRemoved = removeOldStories()
        logger.info("Finishing RemoveOlderStoriesJob removed $storiesRemoved stories")
    }

}
