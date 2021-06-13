package com.danieldisu.hnnotify.framework.hn

import com.danieldisu.hnnotify.framework.hn.data.StoryDTO
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class HackerNewsApi {

    private val webClient = WebClient.create("https://hacker-news.firebaseio.com/v0")

    fun getTopStories(): Mono<List<String>> {
        return webClient.get()
            .uri("/topstories.json")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono()
    }

    fun getNewStories(): Mono<List<String>> {
        return webClient.get()
            .uri("/newstories.json")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono()
    }

    fun getStoryDetail(id: String): Mono<StoryDTO> {
        return webClient.get()
            .uri("/item/{itemId}/.json", id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono()
    }

}
