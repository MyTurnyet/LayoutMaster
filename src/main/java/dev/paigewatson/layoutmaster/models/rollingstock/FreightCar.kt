package dev.paigewatson.layoutmaster.models.rollingstock

import dev.paigewatson.layoutmaster.models.goods.GoodsType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
 * FreightCar
 * Should know it's state
 * road name, road number, car type, what goods it can carry
 * if loaded or not
 */
@Document(collection = "FreightCars")
class FreightCar(uuid: UUID, var roadName: String?, var roadNumber: Int, var carType: AARType?) : RollingStock {
    @Id
    var id: String = ""
    var currentlyCarriedGoods = GoodsType.EMPTY

    init {
        id = uuid.toString()
    }

    fun canCarry(expectedGood: GoodsType?): Boolean {
        return carType!!.canCarry(expectedGood!!)
    }

    override fun load(goodsType: GoodsType) {
        if (!canCarry(goodsType)) return
        currentlyCarriedGoods = goodsType
    }

    override val isNull: Boolean
        get() = false

    fun displayName(): String {
        return carType!!.displayName().toString() + " - " + roadName + " " + roadNumber
    }

    override fun toString(): String {
        return "FreightCar{" +
                "id='" + id + '\'' +
                ", roadName='" + roadName + '\'' +
                ", roadNumber=" + roadNumber +
                ", carType=" + carType +
                ", currentlyCarriedGoods=" + currentlyCarriedGoods +
                '}'
    }


    override fun isAARType(expectedAARDesignation: AARDesignation): Boolean {
        return carType!!.isOfType(expectedAARDesignation)
    }

    override fun isCarrying(goodsType: GoodsType): Boolean {
        return currentlyCarriedGoods === goodsType
    }
}
