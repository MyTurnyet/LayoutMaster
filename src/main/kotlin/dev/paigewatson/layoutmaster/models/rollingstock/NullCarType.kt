package dev.paigewatson.layoutmaster.models.rollingstock

import dev.paigewatson.layoutmaster.data.CarTypeDAL
import dev.paigewatson.layoutmaster.models.goods.GoodsType

class NullCarType : BaseCarType() {


    override fun canCarry(goodsType: GoodsType): Boolean {
        return false
    }

    override fun isOfType(designation: AARDesignation): Boolean {
        return false
    }

    override fun displayName(): AARDesignation {
        return AARDesignation.NULL
    }

    override fun saveToDatabase(carTypeDAL: CarTypeDAL): CarType {
        return this
    }

    override val isNull: Boolean
        get() = true

    override fun toString(): String {
        return "NullCarType{" +
                "id=''" +
                ", aarDesignation=''" +
                ", carriedGoodsList=''" +
                "}"
    }

    init {
        aarDesignation = AARDesignation.NULL
        carriedGoodsList = emptyList()
        id = ""
    }
}
