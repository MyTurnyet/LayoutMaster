package dev.paigewatson.layoutmaster.client.services

import dev.paigewatson.layoutmaster.data.RollingStockDAL
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MongoFreightCarService(@param:Autowired private val rollingStockDAL: RollingStockDAL) : FreightCarService {
    override fun allFreightCars(): List<RollingStock> {
        return rollingStockDAL.allRollingStock
    }

    override fun allFreightCarsByAARType(aarDesignation: AARDesignation): List<RollingStock> {
        return rollingStockDAL.getAllOfAARDesignation(aarDesignation)
    }

    override fun delete(rollingStockToDelete: RollingStock) {
        rollingStockDAL.delete(rollingStockToDelete)
    }

    override fun allFreightCarsByRoadName(roadName: String): List<RollingStock> {
        return rollingStockDAL.getAllOfRoadName(roadName)
    }

    override fun saveFreightCarToDatabase(freightCarToSave: RollingStock): RollingStock {
        return rollingStockDAL.insertRollingStock(freightCarToSave)
    }
}
