package dev.paigewatson.layoutmaster.data

import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.CarType

interface CarTypeDAL {
    fun findAllByCarTypesThatCanCarry(expectedGoods: GoodsType): List<CarType>
    fun findByAarType(aarDesignation: AARDesignation): CarType
    fun deleteAll()
    fun insertCarType(carTypeToSave: CarType): CarType
    fun delete(carTypeToDelete: CarType)
    val allCarTypes: List<CarType>
    val allAARDesignations: List<AARDesignation>
}
