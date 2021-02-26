package dev.paigewatson.layoutmaster.data

import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.AARType
import dev.paigewatson.layoutmaster.models.rollingstock.CarType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

class CarTypeDALTests {
    private val collectionName = "AARTypes"

    @Nested
    @Tag("Unit")
    inner class UnitTests {
        private val mongoTemplate: MongoTemplate = Mockito.mock(MongoTemplate::class.java)
        private val carTypeMongoDAL: CarTypeMongoDAL = CarTypeMongoDAL(mongoTemplate)

        @Test
        fun should_returnDistinct_AARDesignations() {
            Mockito.`when`(
                mongoTemplate.findDistinct(
                    "aarDesignation",
                    AARType::class.java,
                    AARDesignation::class.java
                )
            )
                .thenReturn(Arrays.asList(AARDesignation.XM, AARDesignation.FC))

            //act
            val allCarTypes = carTypeMongoDAL.allAARDesignations

            //assert
            Assertions.assertThat(allCarTypes.size).isEqualTo(2)
            Mockito.verify(mongoTemplate)
                .findDistinct("aarDesignation", AARType::class.java, AARDesignation::class.java)
        }

        @Test
        fun should_returnAllInCollection() {
            //assign
            val boxcarType = TestAARTypeCreator.boxcarType()
            val gondolaType = TestAARTypeCreator.gondolaType()
            val flatcarType = TestAARTypeCreator.flatcarType()
            Mockito.`when`(mongoTemplate.findAll(AARType::class.java, collectionName))
                .thenReturn(listOfNotNull(boxcarType, flatcarType, gondolaType))

            //act
            val allCarTypes = carTypeMongoDAL.allCarTypes

            //assert
            Assertions.assertThat(allCarTypes.size).isEqualTo(3)
            Mockito.verify(mongoTemplate).findAll(AARType::class.java, collectionName)
        }

        @Test
        fun should_deleteAllRecordsInCollection() {
            carTypeMongoDAL.deleteAll()
            Mockito.verify(mongoTemplate).remove(Query(), collectionName)
        }

        @Test
        fun should_deleteRecord() {
            //assign
            val boxcarType: CarType = TestAARTypeCreator.boxcarType()
            //act
            carTypeMongoDAL.delete(boxcarType)
            //assert
            Mockito.verify(mongoTemplate).remove(boxcarType, collectionName)
        }

        @Test
        fun should_insertCarType() {
            //assign
            val boxcarType: CarType = TestAARTypeCreator.boxcarType()

            //act
            carTypeMongoDAL.insertCarType(boxcarType)
            //assert
            Mockito.verify(mongoTemplate).findAndRemove(any(), AARType::class.java, collectionName)
            Mockito.verify(mongoTemplate).insert(boxcarType, collectionName)
        }

        @Test
        fun should_removeExistingThen_insertCarType() {
            //assign
            val boxcarTypeToAdd: CarType = TestAARTypeCreator.boxcarType()

            //act
            carTypeMongoDAL.insertCarType(boxcarTypeToAdd)
            //assert
            Mockito.verify(mongoTemplate).findAndRemove(
                Query.query(Criteria.where("aarDesignation").`is`(AARDesignation.XM)),
                AARType::class.java,
                collectionName
            )
            Mockito.verify(mongoTemplate).insert(boxcarTypeToAdd, collectionName)
        }

        @Test
        fun should_queryWhereAARType_isXM() {
            //assign
            val boxcarType = AARType(AARDesignation.XM, Arrays.asList(GoodsType.Ingredients, GoodsType.Logs))
            val query = Query.query(Criteria.where("aarDesignation").`is`(AARDesignation.XM))
            Mockito.`when`(mongoTemplate.findOne(query, AARType::class.java, collectionName)).thenReturn(boxcarType)

            //act
            val byAarType = carTypeMongoDAL.findByAarType(AARDesignation.XM)

            //assert
            Mockito.verify(mongoTemplate).findOne(query, AARType::class.java, collectionName)
            Assertions.assertThat(byAarType).isEqualTo(boxcarType)
        }

        @Test
        fun should_returnNullCarType_whenNoCarTypeByAAR_exists() {
            //assign
            val query = Query.query(Criteria.where("aarDesignation").`is`(AARDesignation.XM))
            Mockito.`when`(mongoTemplate.findOne(query, CarType::class.java)).thenReturn(null)

            //act
            val byAarType = carTypeMongoDAL.findByAarType(AARDesignation.XM)

            //assert
            Assertions.assertThat(byAarType.isNull).isTrue
        }

    }

    @DataMongoTest
    @ExtendWith(SpringExtension::class)
    @Tag("Mongo")
    @Nested
    inner class DataTests(@Autowired mongoTemplate: MongoTemplate) {
        private val carTypeMongoDAL: CarTypeDAL
        private val mongoTemplate: MongoTemplate
        private var boxcarType: CarType? = null
        private var boxcarType2: CarType? = null
        private var boxcarUUID: UUID? = null

        @BeforeEach
        fun setUp() {
            mongoTemplate.remove(Query(), collectionName)
            boxcarUUID = UUID.randomUUID()
            boxcarType = TestAARTypeCreator.boxcarType(boxcarUUID)
            boxcarType2 = TestAARTypeCreator.getLoadedCarType(
                UUID.randomUUID(),
                AARDesignation.XM,
                Arrays.asList(GoodsType.Paper, GoodsType.Parts, GoodsType.Logs)
            )
        }

        @Test
        fun should_returnDistinct_AARDesignations() {
            insertCarTypesForTesting()
            val gondola = TestAARTypeCreator.gondolaType()
            val flatcar = TestAARTypeCreator.flatcarType()
            mongoTemplate.insert(gondola, collectionName)
            mongoTemplate.insert(flatcar, collectionName)
            //act
            val allCarTypes = carTypeMongoDAL.allAARDesignations

            //assert
            Assertions.assertThat(allCarTypes.size).isEqualTo(3)
            val expectedTypes = Arrays.asList(AARDesignation.XM, AARDesignation.FC, AARDesignation.GS)
            Assertions.assertThat(allCarTypes).hasSameElementsAs(expectedTypes)
        }

        @Test
        fun should_SaveCarTypes_ToDatabase() {
            //assign
            //act
            carTypeMongoDAL.insertCarType(boxcarType2!!)
            //assert
            val allExistingCarTypes = mongoTemplate.findAll(AARType::class.java)
            Assertions.assertThat(allExistingCarTypes.size).isEqualTo(1)
        }

        @Test
        fun should_replaceExistingWhenCalling_insertCarTypes_ToDatabase() {
            //assign
            insertCarTypesForTesting()
            //act
            carTypeMongoDAL.insertCarType(boxcarType2!!)
            //assert
            val allExistingCarTypes = mongoTemplate.findAll(AARType::class.java)
            Assertions.assertThat(allExistingCarTypes.size).isEqualTo(3)
        }

        @Test
        fun should_returnAllCarTypes() {
            //assign
            insertCarTypesForTesting()

            //act
            val allCarTypes = carTypeMongoDAL.allCarTypes

            //assert
            Assertions.assertThat(allCarTypes.size).isEqualTo(3)
        }

        @Test
        fun should_findByAARType() {
            //assign
            insertCarTypesForTesting()

            //act
            val byAarType = carTypeMongoDAL.findByAarType(AARDesignation.XM)

            //assert
            Assertions.assertThat(byAarType.toString()).isEqualTo(boxcarType.toString())
        }

        @Test
        fun should_returnAllTypesThatCanCarry_Parts() {
            //assign
            insertCarTypesForTesting()

            //act
            val allByCarTypesThatCanCarry = carTypeMongoDAL.findAllByCarTypesThatCanCarry(GoodsType.Parts)

            //assert
            Assertions.assertThat(allByCarTypesThatCanCarry.size).isEqualTo(2)
            Assertions.assertThat(
                allByCarTypesThatCanCarry.stream()
                    .allMatch { aarType: CarType -> aarType.canCarry(GoodsType.Parts) }).isTrue
        }

        @Test
        fun should_returnEmptyListOf_AllTypesThatCanCarry_Paper() {
            //assign
            insertCarTypesForTesting()

            //act
            val allByCarTypesThatCanCarry = carTypeMongoDAL.findAllByCarTypesThatCanCarry(GoodsType.Paper)

            //assert
            Assertions.assertThat(allByCarTypesThatCanCarry.size).isEqualTo(0)
        }

        private fun insertCarTypesForTesting() {
            val boxcar = TestAARTypeCreator.boxcarType(boxcarUUID)
            val gondola = TestAARTypeCreator.gondolaType()
            val flatcar = TestAARTypeCreator.flatcarType()
            val aarTypes = Arrays.asList(boxcar, gondola, flatcar)
            for (aarType in aarTypes) {
                mongoTemplate.insert(aarType, collectionName)
            }
        }

        init {
            carTypeMongoDAL = CarTypeMongoDAL(mongoTemplate)
            this.mongoTemplate = mongoTemplate
        }
    }
}
