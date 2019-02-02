package com.danieldisu.hnnotify.application

import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.framework.repositories.interest.InterestRepository
import com.danieldisu.hnnotify.framework.repositories.interest.data.InterestDBO
import com.danieldisu.hnnotify.framework.repositories.interest.data.UserDBO
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AddNewInterestForUser(private val interestRepository: InterestRepository) {

    fun execute(interest: Interest): Mono<Unit> {
        return Mono.fromCallable {
            interestRepository.save(
                InterestDBO(
                    user = UserDBO(id = interest.userId),
                    interestName = interest.interestName
                )
            )
        }
    }

}