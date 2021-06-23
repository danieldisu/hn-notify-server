package com.danieldisu.hnnotify.application

import com.danieldisu.hnnotify.framework.repositories.data.InterestDBO
import com.danieldisu.hnnotify.framework.repositories.data.UserDBO
import com.danieldisu.hnnotify.framework.repositories.interest.InterestRepository
import com.danieldisu.hnnotify.framework.repositories.user.UserNotFound
import com.danieldisu.hnnotify.framework.repositories.user.UserRepository
import com.danieldisu.hnnotify.framework.repositories.user.findByIdMono
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AddNewInterestForUser(
    private val interestRepository: InterestRepository,
    private val userRepository: UserRepository
) {

    fun execute(request: AddInterestRequest): Mono<Unit> {
        return userRepository.findByIdMono(request.userId)
            .flatMap { saveInterest(request, it) }
            .onErrorMap(::mapErrorToDomainException)
    }

    private fun saveInterest(request: AddInterestRequest, userDBO: UserDBO): Mono<Unit> =
        Mono.fromCallable {
            interestRepository.save(
                InterestDBO.from(
                    user = userDBO,
                    interestName = request.interestName,
                    interestKeywords = request.interestKeywords
                )
            )
        }.map { }

    private fun mapErrorToDomainException(cause: Throwable): Throwable {
        return when (cause) {
            is UserNotFound -> AddNewInterestError.UserNotFound(cause)
            is DataIntegrityViolationException -> AddNewInterestError.InterestAlreadyPresent(cause)
            else -> AddNewInterestError.Unknown(cause)
        }
    }

}

data class AddInterestRequest(
    val userId: String,
    val interestName: String,
    val interestKeywords: List<String>,
)

sealed class AddNewInterestError : Exception() {
    data class UserNotFound(override val cause: Throwable) : AddNewInterestError()
    data class InterestAlreadyPresent(override val cause: Throwable) : AddNewInterestError()
    data class Unknown(override val cause: Throwable) : AddNewInterestError()
}

