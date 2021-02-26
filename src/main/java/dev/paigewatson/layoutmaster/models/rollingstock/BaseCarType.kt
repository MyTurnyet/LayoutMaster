package dev.paigewatson.layoutmaster.models.rollingstock

import dev.paigewatson.layoutmaster.models.goods.GoodsType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "AARTypes")
abstract class BaseCarType : CarType {
    @JvmField
    var aarDesignation: AARDesignation = AARDesignation.NULL

    @JvmField
    var carriedGoodsList: List<GoodsType> = Collections.emptyList()

    @JvmField
    @Id
    var id = ""

    constructor()
    constructor(uuid: UUID, aarDesignation: AARDesignation, carriedGoodsList: List<GoodsType>) {
        id = uuid.toString()
        this.aarDesignation = aarDesignation
        this.carriedGoodsList = carriedGoodsList
    }

    override fun isOfType(expectedTypeDesignation: AARDesignation): Boolean {
        return expectedTypeDesignation == aarDesignation
    }
}
