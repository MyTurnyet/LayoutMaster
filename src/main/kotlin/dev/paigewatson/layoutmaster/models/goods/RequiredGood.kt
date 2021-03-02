package dev.paigewatson.layoutmaster.models.goods

/**
 * RequiredGood
 * Know what type it needs
 */
class RequiredGood @JvmOverloads constructor(
    private val neededGoodsType: GoodsType,
    private val isAssigned: Boolean = false
) : DeliverableGood {
    fun needs(goodsType: GoodsType): Boolean {
        return goodsType === neededGoodsType && !isAssigned
    }

    override fun toString(): String {
        return "RequiredGood{" +
                "neededGoodsType=" + neededGoodsType +
                ", isAssigned=" + isAssigned +
                '}'
    }
}
