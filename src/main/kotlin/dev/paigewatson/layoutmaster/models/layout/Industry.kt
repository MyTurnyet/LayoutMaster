package dev.paigewatson.layoutmaster.models.layout

import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.goods.ProducedGood
import dev.paigewatson.layoutmaster.models.goods.RequiredGood
import org.springframework.data.annotation.Id
import java.util.*

/**
 * Industry
 * Should know it's name and take in goods,
 * can produce goods
 * knows what rolling stock are at it's location and can load/unload them
 * knows how many cars it can hold
 */
class Industry(
    private val industryName: String,
    private val acceptedGoodsList: ArrayList<RequiredGood>,
    private val producedGoods: ArrayList<ProducedGood>,
    private val industryTracks: ArrayList<IndustryTrack>
) {
    @Id
    private val id: String? = null
    fun name(): String {
        return industryName
    }

    fun needs(goodsType: GoodsType?): Boolean {
        return acceptedGoodsList.stream().anyMatch { requiredGood: RequiredGood -> requiredGood.needs(goodsType!!) }
    }

    fun produces(goodsType: GoodsType?): Boolean {
        return producedGoods.stream().anyMatch { producedGood: ProducedGood -> producedGood.isOfType(goodsType!!) }
    }

    override fun toString(): String {
        return "Industry{" +
                "id='" + id + '\'' +
                ", industryName='" + industryName + '\'' +
                ", acceptedGoodsList=" + acceptedGoodsList +
                ", producedGoods=" + producedGoods +
                ", industryTracks=" + industryTracks +
                '}'
    }
}
