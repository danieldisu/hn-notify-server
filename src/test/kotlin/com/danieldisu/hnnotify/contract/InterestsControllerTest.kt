package com.danieldisu.hnnotify.contract

import com.danieldisu.hnnotify.framework.controllers.data.InterestDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class InterestsControllerTest {

    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val userId = "1"

    @Test
    fun `should return 201 CREATED when successfully added the interest to the database`() {
        val url = "http://localhost:$port/user/$userId/interests"

        val interestDTO = InterestDTO("kubernetes")

        val postForEntity = restTemplate.postForEntity<Unit>(url, interestDTO)

        assertThat(postForEntity.statusCode).isEqualTo(HttpStatus.CREATED)
    }
}
