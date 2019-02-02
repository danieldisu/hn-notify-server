package com.danieldisu.hnnotify.framework.jobs

import com.danieldisu.hnnotify.application.FetchLatestNewStories
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

const val tenMinutes = 600000L
const val twentySeconds = 20000L

@Component
class FetchLatestNewStoriesJob(private val fetchLatestNewStories: FetchLatestNewStories) {

    private val logger = LoggerFactory.getLogger(javaClass)


    @Scheduled(fixedRate = twentySeconds)
    fun fetchLatestNewStories() {
        fetchLatestNewStories.execute()
            .subscribe(
                {
                    logger.debug("Correctly fetched ${it.size} new stories")
                },
                {
                    logger.error("Error fetching new stories", it)
                })
    }

}