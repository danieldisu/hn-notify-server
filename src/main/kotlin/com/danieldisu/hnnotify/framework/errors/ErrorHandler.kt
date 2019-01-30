package com.danieldisu.hnnotify.framework.errors

interface ErrorHandler {

    fun handle(throwable: Throwable)

}