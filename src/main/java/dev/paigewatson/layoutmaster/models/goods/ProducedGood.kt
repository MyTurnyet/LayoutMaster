package dev.paigewatson.layoutmaster.models.goods

class ProducedGood @JvmOverloads constructor(private val goodsType: GoodsType, val isAssigned: Boolean = false) :
    DeliverableGood {
    fun isOfType(expectedType: GoodsType): Boolean {
        return expectedType === goodsType
    }

    override fun toString(): String {
        return "ProducedGood{" +
                "goodsType=" + goodsType +
                ", isAssigned=" + isAssigned +
                '}'
    }
}
