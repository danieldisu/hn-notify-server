package com.danieldisu.hnnotify.framework.errors

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PrintErrorHandler : ErrorHandler {

  private val logger = LoggerFactory.getLogger(javaClass)

  override fun handle(throwable: Throwable) {
    logger.error("handledError", throwable)
  }

}
