package dev.paigewatson.layoutmaster.data

import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock

interface RollingStockDAL {
    val allRollingStock: List<RollingStock>
    fun deleteAll()
    fun delete(rollingStockToDelete: RollingStock)
    fun getAllOfAARDesignation(aarDesignation: AARDesignation): List<RollingStock>
    fun getAllOfRoadName(roadName: String): List<RollingStock>
    fun insertRollingStock(rollingStockToSave: RollingStock): RollingStock
}
