package dev.paigewatson.layoutmaster

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LayoutMasterApplication     //start the application

fun main(args: Array<String>) {
    runApplication<LayoutMasterApplication>(*args)
}
