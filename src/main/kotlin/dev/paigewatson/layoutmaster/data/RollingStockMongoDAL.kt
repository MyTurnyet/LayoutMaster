package dev.paigewatson.layoutmaster.data

import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.util.*

@Service
class RollingStockMongoDAL(@param:Autowired private val mongoTemplate: MongoTemplate) : RollingStockDAL {
    private val collectionName = "FreightCars"
    override val allRollingStock: List<RollingStock>
        get() {
            val allReturnedFreightCars = mongoTemplate.findAll(FreightCar::class.java, collectionName)
            return Collections.unmodifiableList(allReturnedFreightCars)
        }

    override fun deleteAll() {
        mongoTemplate.remove(Query(), collectionName)
    }

    override fun delete(rollingStockToDelete: RollingStock) {
        mongoTemplate.remove(rollingStockToDelete, collectionName)
    }

    override fun getAllOfAARDesignation(aarDesignation: AARDesignation): List<RollingStock> {
        val freightCarsByAARList = mongoTemplate.find(
            Query.query(
                Criteria.where("carType.aarDesignation").`is`(aarDesignation.name)
            ),
            FreightCar::class.java,
            collectionName
        )
        return Collections.unmodifiableList(freightCarsByAARList)
    }

    override fun getAllOfRoadName(roadName: String): List<RollingStock> {
        val freightCarList = mongoTemplate.find(
            Query.query(
                Criteria.where("roadName").`is`(roadName)
            ),
            FreightCar::class.java,
            collectionName
        )
        return Collections.unmodifiableList(freightCarList)
    }

    override fun insertRollingStock(rollingStockToSave: RollingStock): RollingStock {
        return mongoTemplate.insert(rollingStockToSave, collectionName)
    }
}
