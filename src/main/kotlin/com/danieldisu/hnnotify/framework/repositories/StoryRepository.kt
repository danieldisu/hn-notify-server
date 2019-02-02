package com.danieldisu.hnnotify.framework.repositories

import com.danieldisu.hnnotify.framework.repositories.data.StoryDBO
import org.springframework.data.repository.Repository

interface StoryRepository : Repository<StoryDBO, String> {

    fun findAll(): List<StoryDBO>

    fun save(storyDBO: StoryDBO)
}