package dev.paigewatson.layoutmaster

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class LayoutMasterApplication     //start the application

fun main(args: Array<String>) {
    runApplication<LayoutMasterApplication>(*args)
}
