package dev.paigewatson.layoutmaster.models.rollingstock

import dev.paigewatson.layoutmaster.data.CarTypeDAL
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "AARTypes")
class AARType : BaseCarType {
    constructor()
    constructor(aarDesignation: AARDesignation, carriedGoodsList: List<GoodsType>) : super(
        UUID.randomUUID(),
        aarDesignation,
        carriedGoodsList
    )

    constructor(uuid: UUID?, aarDesignation: AARDesignation, carriedGoodsList: List<GoodsType>) : super(
        uuid!!,
        aarDesignation,
        carriedGoodsList
    )

    override fun canCarry(expectedGoodsType: GoodsType): Boolean {
        return carriedGoodsList.contains(expectedGoodsType)
    }

    override fun displayName(): AARDesignation {
        return aarDesignation
    }

    override fun saveToDatabase(carTypeDAL: CarTypeDAL): CarType {

        val byAarType = carTypeDAL.findByAarType(aarDesignation)
        if (!byAarType.isNull) {
            carTypeDAL.delete(byAarType)
        }
        return carTypeDAL.insertCarType(this)
    }

    override val isNull: Boolean
        get() = false


    override fun toString(): String {
        return "AARType{" +
                "id='" + id + "'" +
                ", aarDesignation=" + aarDesignation +
                ", carriedGoodsList=" + carriedGoodsList +
                "}"
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is AARType) return false
        val aarType = o
        return aarDesignation == aarType.aarDesignation && carriedGoodsList == aarType.carriedGoodsList && id == aarType.id
    }

    override fun hashCode(): Int {
        return Objects.hash(aarDesignation, carriedGoodsList, id)
    }

}
