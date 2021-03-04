package dev.paigewatson.layoutmaster.models.rollingstock

import dev.paigewatson.layoutmaster.data.CarTypeDAL
import dev.paigewatson.layoutmaster.models.NullableEntity
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import java.io.Serializable

interface CarType : NullableEntity, Serializable {
    fun canCarry(goodsType: GoodsType): Boolean
    fun isOfType(designation: AARDesignation): Boolean
    fun displayName(): AARDesignation
    fun saveToDatabase(carTypeDAL: CarTypeDAL): CarType
}
