package com.danieldisu.hnnotify.framework.controllers

import com.danieldisu.hnnotify.application.AddNewInterestForUser
import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.framework.controllers.data.InterestDTO
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI

@RestController
@RequestMapping("/user/{userId}/interests")
class InterestsController(private val addNewInterestForUser: AddNewInterestForUser) {

    @PostMapping("", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun addInterest(
        @PathVariable userId: String,
        @RequestBody interest: InterestDTO
    ): Mono<ResponseEntity<Unit>> {
        return addNewInterestForUser.execute(Interest(interest.interestName, userId))
            .map { ResponseEntity.created(URI("/user/$userId/interests")).build<Unit>() }
    }

}