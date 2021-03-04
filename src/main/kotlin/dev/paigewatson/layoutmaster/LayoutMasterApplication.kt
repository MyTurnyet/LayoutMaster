package dev.paigewatson.layoutmaster

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class LayoutMasterApplication     //start the application

fun main(args: Array<String>) {
    runApplication<LayoutMasterApplication>(*args)
}
