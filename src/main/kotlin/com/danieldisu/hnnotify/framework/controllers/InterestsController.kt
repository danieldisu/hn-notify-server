package com.danieldisu.hnnotify.framework.controllers

import com.danieldisu.hnnotify.application.AddInterestRequest
import com.danieldisu.hnnotify.application.AddNewInterestError
import com.danieldisu.hnnotify.application.AddNewInterestForUser
import com.danieldisu.hnnotify.application.GetAllUserInterests
import com.danieldisu.hnnotify.framework.controllers.data.InterestDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/user/{userId}/interests")
class InterestsController(
    private val addNewInterestForUser: AddNewInterestForUser,
    private val getAllUserInterests: GetAllUserInterests
) {

    private val logger: Logger = LoggerFactory.getLogger(InterestsController::class.java)

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addInterest(
        @PathVariable userId: String,
        @RequestBody interest: InterestDTO
    ): Mono<Unit> {
        val request = AddInterestRequest(
            userId = userId,
            interestName = interest.interestName,
            interestKeywords = interest.interestKeywords
        )
        return addNewInterestForUser.execute(request)
    }

    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllInterests(@PathVariable userId: String): Mono<List<InterestDTO>> {
        return getAllUserInterests.execute(userId)
            .map { interests -> interests.map { InterestDTO(it.interestName, it.interestKeywords) } }
    }

    @ExceptionHandler
    fun handle(exception: Exception): ResponseEntity<Unit> {
        logger.error("InterestsControllerException", exception)
        return when (exception) {
            is AddNewInterestError.InterestAlreadyPresent -> ResponseEntity.status(HttpStatus.CONFLICT).build()
            is AddNewInterestError.UserNotFound -> ResponseEntity.status(HttpStatus.CONFLICT).build()
            is AddNewInterestError.Unknown -> ResponseEntity.status(500).build()
            else -> ResponseEntity.status(500).build()
        }
    }
}
