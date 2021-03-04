package dev.paigewatson.layoutmaster.helpers

import dev.paigewatson.layoutmaster.client.services.CarTypeService
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.CarType

class CarTypeServiceFake : CarTypeService {
    private var returnedCarTypeList: List<CarType>? = null
    private var savedEntity: CarType? = null
    private var returnedCarTypeWithAAR: CarType? = null
    private var returnedAARTypes: List<AARDesignation> = emptyList()
    override fun allAARDesignations(): List<AARDesignation> {
        return returnedAARTypes
    }

    override fun saveCarTypeToDatabase(carTypeToSave: CarType): CarType {
        savedEntity = carTypeToSave
        return carTypeToSave
    }

    override fun allCarTypes(): List<CarType> {
        return returnedCarTypeList!!
    }

    override fun carTypeForAAR(expectedAARDesignation: AARDesignation): CarType {
        return returnedCarTypeWithAAR!!
    }

    override fun carTypesThatCarryGoodsType(expectedGoodsType: GoodsType): List<CarType> {
        return returnedCarTypeList!!
    }

    fun setReturnedCarTypeList(returnedCarTypeList: List<CarType>?) {
        this.returnedCarTypeList = returnedCarTypeList
    }

    fun savedDtoEntity(): CarType? {
        return savedEntity
    }

    fun setReturnedCarTypeWithAAR(carType: CarType?) {
        returnedCarTypeWithAAR = carType
    }

    fun setReturnAARTypes(returnedAARTypes: List<AARDesignation>) {
        this.returnedAARTypes = returnedAARTypes
    }
}
