package dev.paigewatson.layoutmaster.data

import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.FreightCar
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

class RollingStockDALTests {
    private val collectionName = "FreightCars"

    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        private val mongoTemplate: MongoTemplate = Mockito.mock(MongoTemplate::class.java)
        private val rollingStockMongoDAL: RollingStockDAL
        private var boxCar1234: FreightCar =
            FreightCar(UUID.randomUUID(), "PNWR", 1234, TestAARTypeCreator.boxcarType())
        private var boxCar1245: FreightCar = FreightCar(UUID.randomUUID(), "BCR", 1245, TestAARTypeCreator.boxcarType())
        private var flatcar3345: FreightCar =
            FreightCar(UUID.randomUUID(), "BCR", 3345, TestAARTypeCreator.flatcarType())
//        private var gondola3225: FreightCar = FreightCar(UUID.randomUUID(), "BNSF", 3225, TestAARTypeCreator.gondolaType())

        @Test
        fun should_addRollingStockToDatabase() {
            Mockito.`when`(mongoTemplate.insert(flatcar3345, collectionName)).thenReturn(flatcar3345)
            val savedRollingStock = rollingStockMongoDAL.insertRollingStock(flatcar3345)
            Mockito.verify(mongoTemplate).insert(flatcar3345, collectionName)
            AssertionsForClassTypes.assertThat(savedRollingStock).isEqualTo(flatcar3345)
        }

        @Test
        fun should_returnAllInCollection() {
            //assign
            Mockito.`when`(mongoTemplate.findAll(FreightCar::class.java, collectionName))
                .thenReturn(listOfNotNull(boxCar1234, boxCar1245))
            //act
            val allRollingStock = rollingStockMongoDAL.allRollingStock
            //assert
            AssertionsForClassTypes.assertThat(allRollingStock.size).isEqualTo(2)
        }

        @Test
        fun should_deleteAllDocumentsInCollection() {
            //assign
            rollingStockMongoDAL.deleteAll()

            //act
            //assert
            Mockito.verify(mongoTemplate).remove(Query(), collectionName)
        }

        @Test
        fun should_deleteRollingStock() {
            //assign
            rollingStockMongoDAL.delete(boxCar1234)

            //act
            //assert
            Mockito.verify(mongoTemplate).remove(boxCar1234, collectionName)
        }

        @Test
        fun should_returnAllFreightCarsWihRoaName_BCR() {
            Mockito.`when`(
                mongoTemplate.find(
                    Query.query(
                        Criteria.where("roadName").`is`("BCR")
                    ),
                    FreightCar::class.java,
                    collectionName
                )
            ).thenReturn(listOfNotNull(flatcar3345, boxCar1245))
            val allOfAARDesignation = rollingStockMongoDAL.getAllOfRoadName("BCR")
            AssertionsForClassTypes.assertThat(allOfAARDesignation.size).isEqualTo(2)
        }

        @Test
        fun should_returnAllFreightCarOfType() {
            Mockito.`when`(
                mongoTemplate.find(
                    Query.query(
                        Criteria.where("carType.aarDesignation").`is`("XM")
                    ),
                    FreightCar::class.java,
                    collectionName
                )
            ).thenReturn(listOfNotNull(boxCar1234, boxCar1245))
            val allOfAARDesignation = rollingStockMongoDAL.getAllOfAARDesignation(AARDesignation.XM)
            AssertionsForClassTypes.assertThat(allOfAARDesignation.size).isEqualTo(2)
        }

        init {
            rollingStockMongoDAL = RollingStockMongoDAL(mongoTemplate)
        }
    }

    @DataMongoTest
    @ExtendWith(SpringExtension::class)
    @Tag("Mongo")
    @Nested
    inner class DataTests(@Autowired mongoTemplate: MongoTemplate) {
        private val mongoTemplate: MongoTemplate
        private val rollingStockMongoDAL: RollingStockDAL
        private var boxcar1234uuid: UUID = UUID.randomUUID()
        private var boxcar1234: FreightCar = FreightCar(boxcar1234uuid, "PNWR", 1234, TestAARTypeCreator.boxcarType())
        private var boxcar1245: FreightCar = FreightCar(UUID.randomUUID(), "BCR", 1245, TestAARTypeCreator.boxcarType())
        private var flatcar2234: FreightCar =
            FreightCar(UUID.randomUUID(), "PNWR", 2234, TestAARTypeCreator.flatcarType())
        private var gondola4453: FreightCar =
            FreightCar(UUID.randomUUID(), "BNSF", 4453, TestAARTypeCreator.gondolaType())

        @BeforeEach
        fun setUp() {
            mongoTemplate.remove(Query(), collectionName)
        }

        @Test
        fun should_returnAllRollingStock() {
            insertRollingStockForTesting()
            val allRollingStock = rollingStockMongoDAL.allRollingStock
            AssertionsForClassTypes.assertThat(allRollingStock.size).isEqualTo(4)
        }

        @Test
        fun should_returnAllFreightCars_ofTypeXM() {
            //assign
            insertRollingStockForTesting()

            //act
            val allOfAARDesignation = rollingStockMongoDAL.getAllOfAARDesignation(AARDesignation.XM)
            //assert
            AssertionsForClassTypes.assertThat(allOfAARDesignation.size).isEqualTo(2)
            AssertionsForClassTypes.assertThat(
                allOfAARDesignation.stream()
                    .allMatch { rollingStock: RollingStock -> rollingStock.isAARType(AARDesignation.XM) }).isTrue
        }

        @Test
        fun should_returnAllFreightCars_withRoadName_PNWR() {
            //assign
            insertRollingStockForTesting()

            //act
            val allOfAARDesignation = rollingStockMongoDAL.getAllOfRoadName("PNWR")
            //assert
            AssertionsForClassTypes.assertThat(allOfAARDesignation.size).isEqualTo(2)
        }

        @Test
        fun should_insertRollingStock_intoDatabase() {
            //act
            val rollingStock = rollingStockMongoDAL.insertRollingStock(flatcar2234)
            val freightCarList = mongoTemplate.findAll(FreightCar::class.java, collectionName)
            AssertionsForClassTypes.assertThat(rollingStock.toString()).isEqualTo(flatcar2234.toString())
            AssertionsForClassTypes.assertThat(freightCarList.size).isEqualTo(1)
            AssertionsForClassTypes.assertThat(freightCarList[0].toString()).isEqualTo(flatcar2234.toString())
        }

        private fun insertRollingStockForTesting() {
            run {
                val freightCarList: List<FreightCar> = listOfNotNull(boxcar1234, boxcar1245, flatcar2234, gondola4453)
                for (freightCar in freightCarList) {
                    mongoTemplate.insert(freightCar, collectionName)
                }
            }
        }

        init {
            rollingStockMongoDAL = RollingStockMongoDAL(mongoTemplate)
            this.mongoTemplate = mongoTemplate
        }
    }
}
