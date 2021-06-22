package com.danieldisu.hnnotify.framework.repositories.story

import com.danieldisu.hnnotify.framework.repositories.data.StoryDBO
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface StoryRepository : Repository<StoryDBO, String> {

    fun findAll(): List<StoryDBO>

    fun save(storyDBO: StoryDBO)

    fun count(): Long

    fun findAllByOrderByIdAsc(pageable: Pageable): List<StoryDBO>

    fun deleteAllByIdIn(storyIds: List<String>): Int
}
