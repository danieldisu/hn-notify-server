package com.danieldisu.hnnotify.application.interest

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.framework.repositories.interest.InterestRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class EditInterestTest {

    @Autowired
    lateinit var interestRepository: InterestRepository

    private val editInterest by lazy { EditInterest(interestRepository) }

    @Test
    internal fun `should save updated interest in the repository`() {
        val result = editInterest(
            EditInterestRequest(
                userId = userId,
                interestId = interestId,
                interestName = interestName,
                interestKeywords = interestKeywords
            )
        )

        assertThat(result).isEqualTo(
            EditInterestResponse.SuccessfullyUpdated(
                Interest(
                    userId = userId,
                    interestName = interestName,
                    interestKeywords = interestKeywords
                )
            )
        )
    }

    @Test
    internal fun `should return item not found when there is no interest with that id`() {
        val result = editInterest(
            EditInterestRequest(
                userId = userId,
                interestId = interestIdThatDoesNotExists,
                interestName = interestName,
                interestKeywords = interestKeywords
            )
        )

        assertThat(result).isEqualTo(EditInterestResponse.InterestNotFound)
    }

    @Test
    internal fun `should return an error when there is already an interest with that name for that user`() {
        val result = editInterest(
            EditInterestRequest(
                userId = userId,
                interestId = interestId,
                interestName = duplicatedInterestName,
                interestKeywords = interestKeywords
            )
        )

        assertThat(result).isEqualTo(EditInterestResponse.DuplicatedInterestName)
    }

    @Test
    internal fun `should return an error when the query fails`() {
        val mockedInterestRepository = mock<InterestRepository> {
            onGeneric { save(any()) }.thenThrow(IllegalStateException())
            onGeneric { existsById(any()) }.thenReturn(true)
        }
        val editInterestWithMockedRepository = EditInterest(mockedInterestRepository)

        val result = editInterestWithMockedRepository(
            EditInterestRequest(
                userId = userId,
                interestId = interestId,
                interestName = interestName,
                interestKeywords = interestKeywords
            )
        )

        assertThat(result).isInstanceOf(EditInterestResponse.UnknownError::class)
    }

    companion object {
        private val interestIdThatDoesNotExists = "25"

        private val interestId = "1"
        private val userId = "1"
        private val interestName = "kotlin on android"
        private val duplicatedInterestName = "kotlin"
        private val interestKeywords = listOf(
            "kotlin",
            "jvm",
            "coroutines"
        )
    }
}
