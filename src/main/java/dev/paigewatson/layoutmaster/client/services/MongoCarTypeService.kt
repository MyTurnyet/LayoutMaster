package dev.paigewatson.layoutmaster.client.services

import dev.paigewatson.layoutmaster.data.CarTypeDAL
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.CarType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MongoCarTypeService(@param:Autowired private val carTypeDAL: CarTypeDAL) : CarTypeService {
    override fun allAARDesignations(): List<AARDesignation> {
        return carTypeDAL.allAARDesignations
    }

    override fun saveCarTypeToDatabase(carTypeToSave: CarType): CarType {
        carTypeDAL.insertCarType(carTypeToSave)
        return carTypeToSave
    }

    override fun allCarTypes(): List<CarType> {
        return carTypeDAL.allCarTypes
    }

    override fun carTypeForAAR(expectedAARDesignation: AARDesignation): CarType {
        return carTypeDAL.findByAarType(expectedAARDesignation)
    }

    override fun carTypesThatCarryGoodsType(expectedGoodsType: GoodsType): List<CarType> {
        return carTypeDAL.findAllByCarTypesThatCanCarry(expectedGoodsType)
    }
}
