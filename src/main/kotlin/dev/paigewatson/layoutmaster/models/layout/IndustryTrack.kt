package dev.paigewatson.layoutmaster.models.layout

import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar
import java.util.*

class IndustryTrack(private val trackName: String, private val maximumNumberOfCars: Int) {
    private val carsAtIndustry = ArrayList<FreightCar>()
    fun name(): String {
        return trackName
    }

    fun hasOpenSlots(): Boolean {
        return carsAtIndustry.size < maximumNumberOfCars
    }

    fun setOutCar(freightCar: FreightCar) {
        carsAtIndustry.add(freightCar)
    }

    override fun toString(): String {
        return "IndustryTrack{" +
                "trackName='" + trackName + '\'' +
                ", maximumNumberOfCars=" + maximumNumberOfCars +
                ", carsAtIndustry=" + carsAtIndustry +
                '}'
    }
}
