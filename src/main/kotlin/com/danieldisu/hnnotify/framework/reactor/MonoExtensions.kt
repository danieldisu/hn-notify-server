package com.danieldisu.hnnotify.framework.reactor

import reactor.core.publisher.Mono
import kotlin.reflect.KClass

fun <T> Mono<T>.onErrorContinue(): Mono<T> {
    return this.onErrorResume { Mono.empty() }
}