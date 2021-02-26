package dev.paigewatson.layoutmaster.client.controllers

import dev.paigewatson.layoutmaster.client.services.FreightCarService
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inventory")
class FreightCarController(@param:Autowired private val freightCarService: FreightCarService) {
    @get:GetMapping(value = ["/freightcars"])
    val allFreightCars: List<RollingStock>
        get() = freightCarService.allFreightCars()

    @GetMapping(value = ["/freightcars/aar/{aarType}"])
    fun getAllFreightCarsOfAARType(@PathVariable(value = "aarType") aarType: AARDesignation?): List<RollingStock> {
        return freightCarService.allFreightCarsByAARType(aarType!!)
    }

    @GetMapping(value = ["/freightcars/{roadname}"])
    fun getAllFreightCarsWithRoadName(@PathVariable(value = "roadname") roadName: String?): List<RollingStock> {
        return freightCarService.allFreightCarsByRoadName(roadName!!)
    }

    @PostMapping(value = ["/freightcars/add"])
    fun saveFreightCarToDatabase(@RequestBody freightCarToSave: FreightCar?): RollingStock {
        return freightCarService.saveFreightCarToDatabase(freightCarToSave!!)
    }
}
