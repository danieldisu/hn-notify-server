package com.danieldisu.hnnotify.integration

import com.danieldisu.hnnotify.framework.repositories.interest.InterestRepository
import com.danieldisu.hnnotify.framework.repositories.data.InterestDBO
import com.danieldisu.hnnotify.framework.repositories.data.UserDBO
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.util.AssertionErrors.assertEquals


@RunWith(SpringRunner::class)
@DataJpaTest
class InterestRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var interestRepository: InterestRepository

    @Test
    fun `should fetch all interests for a given user`() {
        val userId = givenTwoInterestsForTheSameUser()

        val userInterests = interestRepository.findAllByUserId(userId)

        assertEquals("", 2, userInterests.size)
    }

    @Test
    fun `should not fetch interests for another user`() {
        val userId = givenTwoInterestsForTheTwoUsers()

        val userInterests = interestRepository.findAllByUserId(userId)

        assertEquals("", 1, userInterests.size)
    }

    @Test
    fun `fetch after save should return the saved interest`() {
        val userId = "1"
        val oneInterestDBO = InterestDBO(
            user = UserDBO(userId),
            interestName = "kubernetes"
        )

        interestRepository.save(oneInterestDBO)
        val userInterests = interestRepository.findAllByUserId(userId)

        assertEquals("", 1, userInterests.size)
    }

    private fun givenTwoInterestsForTheTwoUsers(): String {
        val userId = "1"
        val anotherUserId = "2"
        val oneInterestDBO = InterestDBO(
            user = UserDBO(userId),
            interestName = "kubernetes"
        )

        val otherInterestDBO = InterestDBO(
            user = UserDBO(anotherUserId),
            interestName = "kotlin"
        )

        entityManager.persist(oneInterestDBO)
        entityManager.persist(otherInterestDBO)
        entityManager.flush()
        return userId
    }

    private fun givenTwoInterestsForTheSameUser(): String {
        val userId = "1"
        val user = UserDBO(userId)
        val oneInterestDBO = InterestDBO(
            user = user,
            interestName = "kubernetes"
        )

        val otherInterestDBO = InterestDBO(
            user = user,
            interestName = "kotlin"
        )

        entityManager.persist(oneInterestDBO)
        entityManager.persist(otherInterestDBO)
        entityManager.flush()
        return userId
    }
}
