package com.danieldisu.hnnotify.application.interest

import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.framework.repositories.interest.InterestRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class GetInterest(
    private val interestRepository: InterestRepository,
) {

    operator fun invoke(interestId: String): Interest? {
        return interestRepository.findByIdOrNull(interestId)?.toDomain()
    }

}
