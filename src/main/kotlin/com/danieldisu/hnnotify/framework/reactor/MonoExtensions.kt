package com.danieldisu.hnnotify.framework.reactor

import reactor.core.publisher.Mono

fun <T> Mono<T>.onErrorContinue(): Mono<T> {
    return this.onErrorResume { Mono.empty() }
}
