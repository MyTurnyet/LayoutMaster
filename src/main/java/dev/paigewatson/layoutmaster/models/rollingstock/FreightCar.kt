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
class FreightCar : RollingStock {
    @Id
    var id: String? = null
    private var uuid: UUID? = null
    var roadName: String? = null
    var roadNumber = 0
    var carType: AARType? = null
    var currentlyCarriedGoods = GoodsType.EMPTY

    constructor()
    constructor(uuid: UUID, roadName: String?, roadNumber: Int, carType: AARType?) {
        this.uuid = uuid
        id = uuid.toString()
        this.roadName = roadName
        this.roadNumber = roadNumber
        this.carType = carType
    }

    fun canCarry(expectedGood: GoodsType?): Boolean {
        return carType!!.canCarry(expectedGood!!)
    }

    override fun load(goodsToLoad: GoodsType) {
        if (!canCarry(goodsToLoad)) return
        currentlyCarriedGoods = goodsToLoad
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
