package dev.paigewatson.layoutmaster.helpers

import dev.paigewatson.layoutmaster.client.services.FreightCarService
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock

class FreightCarServiceFake : FreightCarService {
    var savedFreightCar: RollingStock? = null
    private var returnedFreightCarsList: List<RollingStock>? = null
    override fun allFreightCars(): List<RollingStock> {
        return returnedFreightCarsList!!
    }

    override fun allFreightCarsByAARType(aarDesignation: AARDesignation): List<RollingStock> {
        return returnedFreightCarsList!!
    }

    override fun delete(rollingStockToDelete: RollingStock) {}
    override fun allFreightCarsByRoadName(roadName: String): List<RollingStock> {
        return returnedFreightCarsList!!
    }

    override fun saveFreightCarToDatabase(freightCarToSave: RollingStock): RollingStock {
        savedFreightCar = freightCarToSave
        return savedFreightCar!!
    }

    fun setReturnedFreightCars(returnedFreightCarsList: List<RollingStock>?) {
        this.returnedFreightCarsList = returnedFreightCarsList
    }
}
