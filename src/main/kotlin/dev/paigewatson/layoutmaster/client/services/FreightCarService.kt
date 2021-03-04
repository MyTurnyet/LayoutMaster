package dev.paigewatson.layoutmaster.client.services

import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock

interface FreightCarService {
    fun allFreightCars(): List<RollingStock>
    fun allFreightCarsByAARType(aarDesignation: AARDesignation): List<RollingStock>
    fun delete(rollingStockToDelete: RollingStock)
    fun allFreightCarsByRoadName(roadName: String): List<RollingStock>
    fun saveFreightCarToDatabase(freightCarToSave: RollingStock): RollingStock
}
