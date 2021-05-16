package com.danieldisu.hnnotify.framework.controllers.response

import com.danieldisu.hnnotify.framework.hn.data.StoryDTO

data class GetUserInterestingStoriesResponse(
    val stories: List<StoryDTO>
)
