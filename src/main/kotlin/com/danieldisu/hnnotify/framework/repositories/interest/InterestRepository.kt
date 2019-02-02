package com.danieldisu.hnnotify.framework.repositories.interest

import com.danieldisu.hnnotify.framework.repositories.interest.data.InterestDBO
import org.springframework.data.repository.Repository


interface InterestRepository : Repository<InterestDBO, String> {

    fun findAllByUserId(userId: String): List<InterestDBO>

    fun save(interestDBO: InterestDBO)

}