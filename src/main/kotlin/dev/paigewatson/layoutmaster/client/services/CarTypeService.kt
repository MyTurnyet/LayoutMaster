package dev.paigewatson.layoutmaster.client.services

import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.CarType

interface CarTypeService {
    fun allAARDesignations(): List<AARDesignation>
    fun saveCarTypeToDatabase(carTypeToSave: CarType): CarType
    fun allCarTypes(): List<CarType>
    fun carTypeForAAR(expectedAARDesignation: AARDesignation): CarType
    fun carTypesThatCarryGoodsType(expectedGoodsType: GoodsType): List<CarType>
}
