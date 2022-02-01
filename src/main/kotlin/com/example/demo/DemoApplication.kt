package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.bind.annotation.*

@EnableTransactionManagement
@SpringBootApplication
@EnableCaching
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
