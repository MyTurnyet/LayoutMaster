package dev.paigewatson.layoutmaster.models.rollingstock

import dev.paigewatson.layoutmaster.data.CarTypeDAL
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "AARTypes")
class AARType(uuid: UUID, aarDesignation: AARDesignation, carriedGoodsList: List<GoodsType>) : BaseCarType(
    uuid,
    aarDesignation,
    carriedGoodsList
) {
//    constructor(aarDesignation: AARDesignation, carriedGoodsList: List<GoodsType>) : super(
//        UUID.randomUUID(),
//        aarDesignation,
//        carriedGoodsList
//    )

    override fun canCarry(goodsType: GoodsType): Boolean {
        return carriedGoodsList.contains(goodsType)
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AARType) return false
        return aarDesignation == other.aarDesignation && carriedGoodsList == other.carriedGoodsList && id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(aarDesignation, carriedGoodsList, id)
    }

}
