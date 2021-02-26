package dev.paigewatson.layoutmaster.client.controllers

import dev.paigewatson.layoutmaster.client.services.CarTypeService
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.CarType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/models")
class CarTypeController(@param:Autowired private val carTypeService: CarTypeService) {
    @get:GetMapping(path = ["/aar"])
    val aARDesignations: List<AARDesignation>
        get() = carTypeService.allAARDesignations()

    @get:GetMapping(path = ["/types"])
    val allCarTypes: List<CarType>
        get() = carTypeService.allCarTypes()

    @PostMapping(path = ["/types/add"])
    fun addNewCarType(@RequestBody carType: CarType) {
        carTypeService.saveCarTypeToDatabase(carType)
    }

    @GetMapping(path = ["/types/aar/{aarType}"])
    fun getCarTypeByAAR(@PathVariable(value = "aarType") expectedType: String?): CarType {
        val aarDesignation = AARDesignation.valueOf(expectedType!!)
        return carTypeService.carTypeForAAR(aarDesignation)
    }

    @GetMapping(path = ["/types/goods/{goodsType}"])
    fun getCarTypesThatCarry(@PathVariable(value = "goodsType") expectedGoods: String?): List<CarType> {
        return carTypeService.carTypesThatCarryGoodsType(
            GoodsType.valueOf(
                expectedGoods!!
            )
        )
    }
}
