package dev.paigewatson.layoutmaster.helpers

import dev.paigewatson.layoutmaster.data.RollingStockDAL
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock

class RollingStockDALFake : RollingStockDAL {
    var savedEntity: RollingStock? = null
    var deletedEntity: RollingStock? = null
    override var allRollingStock: List<RollingStock> = emptyList()
        private set
    private var deleteAllCalledCount = 0
    override fun deleteAll() {
        deleteAllCalledCount++
    }

    override fun delete(rollingStockToDelete: RollingStock) {
        deletedEntity = rollingStockToDelete
    }

    override fun getAllOfAARDesignation(aarDesignation: AARDesignation): List<RollingStock> {
        return allRollingStock
    }

    override fun getAllOfRoadName(roadName: String): List<RollingStock> {
        return allRollingStock
    }

    override fun insertRollingStock(rollingStockToSave: RollingStock): RollingStock {
        savedEntity = rollingStockToSave
        return savedEntity!!
    }

    fun setReturnedValuesList(returnedFreightCarsList: List<RollingStock>) {
        allRollingStock = returnedFreightCarsList
    }

    fun deleteAllCalledCount(): Int {
        return deleteAllCalledCount
    }
}
