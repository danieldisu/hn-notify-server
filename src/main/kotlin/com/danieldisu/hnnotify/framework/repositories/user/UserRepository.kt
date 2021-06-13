package com.danieldisu.hnnotify.framework.repositories.user

import com.danieldisu.hnnotify.framework.repositories.data.UserDBO
import org.springframework.data.repository.CrudRepository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

interface UserRepository : CrudRepository<UserDBO, String>


fun UserRepository.findByIdMono(userId: String): Mono<UserDBO> =
    findById(userId)
        .let {
            if (it.isPresent) {
                it.get().toMono()
            } else {
                Mono.error(UserNotFound(userId))
            }
        }

data class UserNotFound(private val userId: String) : Exception("User with id $userId not found")
