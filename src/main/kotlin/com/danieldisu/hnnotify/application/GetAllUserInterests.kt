package com.danieldisu.hnnotify.application

import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.domain.mappings.toDomain
import com.danieldisu.hnnotify.framework.repositories.data.InterestDBO
import com.danieldisu.hnnotify.framework.repositories.interest.InterestRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetAllUserInterests(private val interestRepository: InterestRepository) {

    fun execute(userId: String): Mono<List<Interest>> {
        return Mono.fromCallable {
            interestRepository.findAllByUserId(userId)
                .map(InterestDBO::toDomain)
        }
    }
}
