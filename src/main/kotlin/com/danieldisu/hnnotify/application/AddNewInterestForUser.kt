package com.danieldisu.hnnotify.application

import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.framework.repositories.data.InterestDBO
import com.danieldisu.hnnotify.framework.repositories.data.UserDBO
import com.danieldisu.hnnotify.framework.repositories.interest.InterestRepository
import com.danieldisu.hnnotify.framework.repositories.user.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AddNewInterestForUser(
    private val interestRepository: InterestRepository,
    private val userRepository: UserRepository
) {

  fun execute(interest: Interest): Mono<Unit> {
    return Mono.fromCallable {
      interestRepository.save(
          InterestDBO(
              user = findOrCreateUser(interest.userId),
              interestName = interest.interestName
          )
      )
    }.onErrorMap(::mapErrorToDomainException).map { Unit }
  }

  private fun mapErrorToDomainException(cause: Throwable): Throwable {
    return if (cause is DataIntegrityViolationException) {
      InterestAlreadyPresent(cause)
    } else {
      UnknownErrorAddingNewInterest(cause)
    }
  }

  private fun findOrCreateUser(userId: String): UserDBO {
    return userRepository.findById(userId)
        .orElse(userRepository.save(UserDBO(userId)))
  }

}

sealed class ErrorAddingNewInterest : Throwable()
data class InterestAlreadyPresent(override val cause: Throwable) : ErrorAddingNewInterest()
data class UnknownErrorAddingNewInterest(override val cause: Throwable) : ErrorAddingNewInterest()
