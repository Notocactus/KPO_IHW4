package se.hse.AuthApi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TicketsApiApplication

fun main(args: Array<String>) {
	runApplication<TicketsApiApplication>(*args)
}
