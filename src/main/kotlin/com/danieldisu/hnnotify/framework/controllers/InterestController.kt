package com.danieldisu.hnnotify.framework.controllers

import com.danieldisu.hnnotify.application.AddInterestRequest
import com.danieldisu.hnnotify.application.AddNewInterestError
import com.danieldisu.hnnotify.application.AddNewInterestForUser
import com.danieldisu.hnnotify.application.GetAllUserInterests
import com.danieldisu.hnnotify.application.interest.EditInterest
import com.danieldisu.hnnotify.application.interest.EditInterestRequest
import com.danieldisu.hnnotify.application.interest.EditInterestResponse
import com.danieldisu.hnnotify.application.interest.GetInterest
import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.framework.controllers.data.InterestDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/user/{userId}/interest")
class InterestController(
    private val addNewInterestForUser: AddNewInterestForUser,
    private val getAllUserInterests: GetAllUserInterests,
    private val editInterest: EditInterest,
    private val getInterest: GetInterest,
) {

    private val logger: Logger = LoggerFactory.getLogger(InterestController::class.java)

    @PostMapping("/{interestId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun editInterest(
        @PathVariable userId: String,
        @PathVariable interestId: String,
        @RequestBody interest: InterestDTO,
    ): ResponseEntity<Unit> {
        val request = EditInterestRequest(
            userId = userId,
            interestName = interest.interestName,
            interestKeywords = interest.interestKeywords,
            interestId = interestId
        )

        return request.toControllerResponse()
    }

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
            .map { interests -> interests.map { it.toDTO() } }
    }

    @GetMapping("/{interestId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getInterestsById(
        @PathVariable userId: String,
        @PathVariable interestId: String,
    ): ResponseEntity<InterestDTO> =
        when (val interest = getInterest(interestId, userId)) {
            null -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(interest.toDTO())
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

    private fun EditInterestRequest.toControllerResponse(): ResponseEntity<Unit> =
        when (val editionResult = editInterest(this)) {
            is EditInterestResponse.SuccessFullyUpdated -> ResponseEntity.noContent().build()
            EditInterestResponse.InterestNotFound -> ResponseEntity.notFound().build()
            is EditInterestResponse.UnknownError -> {
                logger.error("ErrorEditingInterest $this", editionResult.cause)
                ResponseEntity.internalServerError().build()
            }
        }
}

private fun Interest.toDTO() = InterestDTO(this.interestName, this.interestKeywords)
