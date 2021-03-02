package dev.paigewatson.layoutmaster.helpers

import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.AARType
import java.util.*

object TestAARTypeCreator {
    fun getLoadedCarType(uuid: UUID, aarDesignation: AARDesignation, carriedGoodsList: List<GoodsType>): AARType {
        return AARType(uuid, aarDesignation, carriedGoodsList)
    }

    @JvmStatic
    @JvmOverloads
    fun boxcarType(uuid: UUID = UUID.randomUUID()): AARType {
        return getLoadedCarType(
            uuid,
            AARDesignation.XM,
            listOf(GoodsType.Ingredients, GoodsType.Logs, GoodsType.Parts)
        )
    }

    @JvmStatic
    @JvmOverloads
    fun gondolaType(uuid: UUID = UUID.randomUUID()): AARType {
        return getLoadedCarType(
            uuid,
            AARDesignation.GS,
            listOf(GoodsType.ScrapMetal, GoodsType.MetalScraps, GoodsType.Logs, GoodsType.Aggregates)
        )
    }

    fun flatcarType(): AARType {
        return flatcarType(UUID.randomUUID())
    }

    @JvmStatic
    fun flatcarType(uuid: UUID): AARType {
        return getLoadedCarType(
            uuid,
            AARDesignation.FC,
            listOf(GoodsType.Logs, GoodsType.Lumber, GoodsType.Parts)
        )
    }
}
