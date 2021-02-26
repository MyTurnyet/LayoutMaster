package dev.paigewatson.layoutmaster.helpers

import dev.paigewatson.layoutmaster.data.CarTypeDAL
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.CarType
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType

class CarTypeDALFake : CarTypeDAL {
    private var carTypeSavedEntity: CarType = NullCarType()
    private var returnedEntity: CarType = NullCarType()
    override var allCarTypes: List<CarType> = emptyList()
        private set
    override var allAARDesignations: List<AARDesignation> = emptyList()
        private set

    override fun findAllByCarTypesThatCanCarry(expectedGoods: GoodsType): List<CarType> {
        return allCarTypes
    }

    override fun findByAarType(aarDesignation: AARDesignation): CarType {
        return returnedEntity
    }

    override fun deleteAll() {}
    override fun insertCarType(carTypeToSave: CarType): CarType {
        carTypeSavedEntity = carTypeToSave
        return carTypeToSave
    }

    override fun delete(carTypeToDelete: CarType) {}
    fun savedEntity(): CarType? {
        return carTypeSavedEntity
    }

    fun setCurrentSavedEntity(currentSavedEntity: CarType) {
        carTypeSavedEntity = currentSavedEntity
    }

    fun setEntityToReturn(returnedEntity: CarType) {
        this.returnedEntity = returnedEntity
    }

    fun setReturnedEntityList(returnedCarTypes: List<CarType>) {
        allCarTypes = returnedCarTypes
    }

    fun setReturnAARDesignationsList(aarDesignationList: List<AARDesignation>) {
        allAARDesignations = aarDesignationList
    }
}
