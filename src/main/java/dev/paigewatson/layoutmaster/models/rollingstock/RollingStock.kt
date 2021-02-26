package dev.paigewatson.layoutmaster.models.rollingstock

import dev.paigewatson.layoutmaster.models.NullableEntity
import dev.paigewatson.layoutmaster.models.goods.GoodsType

interface RollingStock : NullableEntity {
    fun isAARType(expectedAARDesignation: AARDesignation): Boolean
    fun isCarrying(goodsType: GoodsType): Boolean
    fun load(goodsType: GoodsType)
}
