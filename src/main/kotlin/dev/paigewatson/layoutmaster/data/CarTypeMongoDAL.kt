package dev.paigewatson.layoutmaster.data

import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.AARType
import dev.paigewatson.layoutmaster.models.rollingstock.CarType
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CarTypeMongoDAL(@Autowired private val mongoTemplate: MongoTemplate) : CarTypeDAL {
    private val collectionName = "AARTypes"
    override fun findAllByCarTypesThatCanCarry(expectedGoods: GoodsType): List<CarType> {
        val carTypesThatCarryGoods = mongoTemplate
            .find(
                Query.query(
                    Criteria.where("carriedGoodsList")
                        .`is`(expectedGoods.toString())
                ),
                AARType::class.java
            )
        return Collections.unmodifiableList(carTypesThatCarryGoods)
    }

    override fun findByAarType(aarDesignation: AARDesignation): CarType {
        val returnedCarType = mongoTemplate.findOne(
            Query.query(
                Criteria.where("aarDesignation")
                    .`is`(aarDesignation)
            ),
            AARType::class.java,
            collectionName
        )
        return returnedCarType ?: NullCarType()
    }

    override fun deleteAll() {
        mongoTemplate.remove(Query(), collectionName)
    }

    override fun insertCarType(carTypeToSave: CarType): CarType {
        mongoTemplate.findAndRemove(
            Query.query(
                Criteria.where("aarDesignation")
                    .`is`(carTypeToSave.displayName())
            ),
            AARType::class.java,
            collectionName
        )
        return mongoTemplate.insert(carTypeToSave, collectionName)
    }

    override fun delete(carTypeToDelete: CarType) {
        mongoTemplate.remove(carTypeToDelete, collectionName)
    }

    override val allCarTypes: List<CarType>
        get() {
            val allAARTypes = mongoTemplate.findAll(AARType::class.java, collectionName)
            return Collections.unmodifiableList(allAARTypes)
        }
    override val allAARDesignations: List<AARDesignation>
        get() = mongoTemplate.findDistinct("aarDesignation", AARType::class.java, AARDesignation::class.java)
}
