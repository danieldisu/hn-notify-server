package com.danieldisu.hnnotify.application

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.extracting
import assertk.assertions.isEmpty
import assertk.assertions.isNotNull
import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.domain.data.Story
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Mono

class GetAllInterestingStoriesTest {

    private val getAllUserInterests: GetAllUserInterests = mock()
    private val getAllStories: GetAllStories = mock()

    private val getAllInterestingStories = GetAllInterestingStories(
        getAllStories = getAllStories,
        getAllUserInterests = getAllUserInterests
    )

    @Test
    internal fun `should return an empty list when user has no interests`() {
        givenUserInterests(emptyList())
        givenStories(aListOfStories)

        val result = getAllInterestingStories.execute(aUserId).block()

        assertThat(result)
            .isNotNull()
            .isEmpty()
    }

    @Test
    internal fun `should return an empty list when no stories found`() {
        givenUserInterests(aListOfInterests)
        givenStories(emptyList())

        val result = getAllInterestingStories.execute(aUserId).block()

        assertThat(result)
            .isNotNull()
            .isEmpty()
    }

    @Test
    internal fun `should return all interesting stories when matching interests`() {
        givenUserInterests(aListOfInterests)
        givenStories(aListOfStories)

        val result = getAllInterestingStories.execute(aUserId).block()

        assertThat(result)
            .isNotNull()
            .extracting(Story::id)
            .containsExactlyInAnyOrder("1", "3")
    }

    private fun givenUserInterests(interests: List<Interest>) {
        whenever(getAllUserInterests.execute(aUserId)).thenReturn(Mono.just(interests))
    }

    private fun givenStories(stories: List<Story>) {
        whenever(getAllStories.execute()).thenReturn(Mono.just(stories))
    }

    companion object {
        private const val aUserId = "1234"

        private val aListOfStories = listOf(
            Story("1", "Kotlin is fun"),
            Story("2", "Java is less fun"),
            Story("3", "kotlin won the android crowd"),
        )

        private val aListOfInterests = listOf(
            Interest("kotlin", aUserId, listOf("kotlin"))
        )

    }
}
