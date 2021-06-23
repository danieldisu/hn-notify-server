package com.danieldisu.hnnotify.application.interest

import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.framework.repositories.interest.InterestRepository
import org.springframework.stereotype.Service

@Service
class GetInterest(
    private val interestRepository: InterestRepository,
) {

    operator fun invoke(interestId: String, userId: String): Interest? {
        return interestRepository.findByIdAndUserId(interestId, userId)?.toDomain()
    }

}
