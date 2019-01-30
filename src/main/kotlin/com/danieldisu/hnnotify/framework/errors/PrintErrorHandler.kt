package com.danieldisu.hnnotify.framework.errors

import org.springframework.stereotype.Component

@Component
class PrintErrorHandler : ErrorHandler {

    override fun handle(throwable: Throwable) {
        throwable.printStackTrace()
    }

}