package com.danieldisu.hnnotify.framework.repositories.interest

import com.danieldisu.hnnotify.framework.repositories.data.InterestDBO
import org.springframework.data.repository.CrudRepository


interface InterestRepository : CrudRepository<InterestDBO, String> {

    fun findAllByUserId(userId: String): List<InterestDBO>
}
