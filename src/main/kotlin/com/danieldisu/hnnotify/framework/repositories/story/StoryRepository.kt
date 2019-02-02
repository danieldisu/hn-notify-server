package com.danieldisu.hnnotify.framework.repositories.story

import com.danieldisu.hnnotify.framework.repositories.story.data.StoryDBO
import org.springframework.data.repository.Repository

interface StoryRepository : Repository<StoryDBO, String> {

    fun findAll(): List<StoryDBO>

    fun save(storyDBO: StoryDBO)
}