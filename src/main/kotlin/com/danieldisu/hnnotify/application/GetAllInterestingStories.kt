package com.danieldisu.hnnotify.application

import com.danieldisu.hnnotify.domain.data.Interest
import com.danieldisu.hnnotify.domain.data.Story
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2

@Service
class GetAllInterestingStories(
    private val getAllUserInterests: GetAllUserInterests,
    private val getAllStories: GetAllStories
) {

    fun execute(userId: String): Mono<List<Story>> {
        return getAllUserInterests.execute(userId)
            .zipWith(getAllStories.execute())
            .map { storiesAndInterests: Tuple2<List<Interest>, List<Story>> ->
                filterInterestingStories(storiesAndInterests.t1, storiesAndInterests.t2)
            }
    }

    private fun filterInterestingStories(
        interests: List<Interest>,
        stories: List<Story>
    ): List<Story> {
        return stories.filter { story -> isRelevantToAnInterest(interests, story) }
    }

    private fun isRelevantToAnInterest(
        interests: List<Interest>,
        story: Story
    ) = interests.any { interest -> isRelevantToInterest(story, interest) }

    private fun isRelevantToInterest(
        story: Story,
        interest: Interest
    ) = interest.interestKeywords.any { keyword -> story.title.contains(keyword, true) }

}
