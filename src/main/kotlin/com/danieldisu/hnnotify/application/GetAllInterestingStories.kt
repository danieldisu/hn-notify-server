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
    return stories.filter { story ->
      interests.any { interest ->
        story.title.contains(interest.interestName, true)
      }
    }
  }

}
