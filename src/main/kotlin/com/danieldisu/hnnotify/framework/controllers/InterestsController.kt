package com.danieldisu.hnnotify.framework.controllers

import com.danieldisu.hnnotify.application.*
import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.framework.controllers.data.InterestDTO
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI

@RestController
@RequestMapping("/user/{userId}/interests")
class InterestsController(
    private val addNewInterestForUser: AddNewInterestForUser,
    private val getAllUserInterests: GetAllUserInterests
) {

  @PutMapping("", produces = [MediaType.APPLICATION_JSON_VALUE])
  fun addInterest(
      @PathVariable userId: String,
      @RequestBody interest: InterestDTO
  ): Mono<ResponseEntity<Unit>> {
    return addNewInterestForUser.execute(Interest(interest.interestName, userId))
        .map { ResponseEntity.created(URI("/user/$userId/interests")).build<Unit>() }
  }

  @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getAllInterests(@PathVariable userId: String): Mono<List<InterestDTO>> {
    return getAllUserInterests.execute(userId)
        .map { interests -> interests.map { InterestDTO(it.interestName) } }
  }

  @ExceptionHandler(ErrorAddingNewInterest::class)
  fun handleErrorAddingNewInterest(exception: ErrorAddingNewInterest): ResponseEntity<Unit> {
    return when (exception) {
      is InterestAlreadyPresent -> ResponseEntity.status(HttpStatus.CONFLICT).build()
      is UnknownErrorAddingNewInterest -> ResponseEntity.badRequest().build()
    }
  }
}
