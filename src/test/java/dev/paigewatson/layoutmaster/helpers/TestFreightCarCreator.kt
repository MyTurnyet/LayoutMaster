package dev.paigewatson.layoutmaster.helpers

import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator.boxcarType
import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator.flatcarType
import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator.gondolaType
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar
import java.util.*

object TestFreightCarCreator {
    fun gondola(roadName: String?, roadNumber: Int): FreightCar {
        return gondola(UUID.randomUUID(), roadName, roadNumber)
    }

    @JvmOverloads
    fun gondola(uuid: UUID?, roadName: String?, roadNumber: Int, carTypeUUID: UUID? = UUID.randomUUID()): FreightCar {
        return FreightCar(uuid!!, roadName, roadNumber, gondolaType(carTypeUUID))
    }

    fun flatcar(roadName: String?, roadNumber: Int): FreightCar {
        return flatcar(UUID.randomUUID(), roadName, roadNumber)
    }

    fun flatcar(flatcarUUID: UUID?, roadName: String?, roadNumber: Int): FreightCar {
        return FreightCar(flatcarUUID!!, roadName, roadNumber, flatcarType())
    }

    fun flatcar(flatcarUUID: UUID?, roadName: String?, roadNumber: Int, carTypeUUID: UUID?): FreightCar {
        return FreightCar(flatcarUUID!!, roadName, roadNumber, flatcarType(carTypeUUID))
    }

    fun boxcar(roadName: String?, roadNumber: Int): FreightCar {
        return boxcar(UUID.randomUUID(), roadName, roadNumber, UUID.randomUUID())
    }

    fun boxcar(uuid: UUID?, roadName: String?, roadNumber: Int, carTypeUUID: UUID?): FreightCar {
        return FreightCar(uuid!!, roadName, roadNumber, boxcarType(carTypeUUID))
    }
}
