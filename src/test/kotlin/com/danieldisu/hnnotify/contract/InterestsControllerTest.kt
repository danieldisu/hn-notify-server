package com.danieldisu.hnnotify.contract

import com.danieldisu.hnnotify.framework.controllers.data.InterestDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InterestsControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @Test
    fun `should return 201 CREATED when successfully added the interest to the database`() {
        webClient.put()
            .uri("/user/$userIdThatExists/interest")
            .body(BodyInserters.fromValue(anInterest))
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.CREATED)
    }

    @Test
    fun `should return 409 CONFLICT when user does not exist`() {
        webClient.put()
            .uri("/user/$userIdThatDoesNotExists/interest")
            .body(BodyInserters.fromValue(anotherInterest))
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.CONFLICT)
    }

    @Test
    fun `should return 409 CONFLICT when trying to add an interest that already exists`() {
        webClient.put()
            .uri("/user/$userIdThatExists/interest")
            .body(BodyInserters.fromValue(interestThatAlreadyExists))
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.CONFLICT)
    }

    @Test
    internal fun `should return 204 when successfully editing an interest`() {
        webClient.put()
            .uri("/user/$userIdThatExists/interest/$interestIdThatAlreadyExists")
            .body(BodyInserters.fromValue(updatedInterest))
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.NO_CONTENT)
    }

    @Test
    internal fun `should return 404 when editing an interest that does not exist`() {
        webClient.put()
            .uri("/user/$userIdThatExists/interest/$interestIdThatDoesNotExists")
            .body(BodyInserters.fromValue(updatedInterest))
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.NOT_FOUND)
    }

    companion object {
        private val userIdThatExists = "1"
        private val userIdThatDoesNotExists = "123"

        private val interestIdThatDoesNotExists = "25"
        private val interestIdThatAlreadyExists = "3"
        private val interestThatAlreadyExists = InterestDTO("kotlin", listOf("kotlin"))
        private val updatedInterest = InterestDTO("kotlin", listOf("kotlin", "coroutines"))

        private val anInterest = InterestDTO("java6", listOf("java6"))
        private val anotherInterest = InterestDTO("java7", listOf("java7"))
    }
}

