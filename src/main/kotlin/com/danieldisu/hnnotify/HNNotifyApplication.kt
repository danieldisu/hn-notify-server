package com.danieldisu.hnnotify

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class HNNotifyApplication

fun main(args: Array<String>) {
	runApplication<HNNotifyApplication>(*args)
}

