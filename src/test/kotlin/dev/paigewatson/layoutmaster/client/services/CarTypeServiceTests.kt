package dev.paigewatson.layoutmaster.client.services

import dev.paigewatson.layoutmaster.helpers.CarTypeDALFake
import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.CarType
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.util.*

class CarTypeServiceTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        private var service: CarTypeService? = null
        private var carTypeDALFake: CarTypeDALFake? = null

        @BeforeEach
        fun beforeEachTestRuns() {
            carTypeDALFake = CarTypeDALFake()
            service = MongoCarTypeService(carTypeDALFake!!)
        }

        @Test
        fun should_returnAllAARDesignations() {
            //assign
            carTypeDALFake!!.setReturnAARDesignationsList(
                Arrays.asList(
                    AARDesignation.XM,
                    AARDesignation.GS,
                    AARDesignation.FC
                )
            )
            //act
            val aarDesignations = service!!.allAARDesignations()
            //assert
            AssertionsForClassTypes.assertThat(aarDesignations.size).isEqualTo(3)
        }

        @Test
        fun should_returnListOfAllExistingCarTypesFromDatabase() {
            //assign
            val boxcarType: CarType = TestAARTypeCreator.boxcarType()
            val returnedCarTypes = listOf(boxcarType)
            carTypeDALFake!!.setReturnedEntityList(returnedCarTypes)

            //act
            val allCarTypesList = service!!.allCarTypes()
            //assert
            AssertionsForClassTypes.assertThat(allCarTypesList.size).isEqualTo(1)
            AssertionsForClassTypes.assertThat(allCarTypesList[0]).isEqualTo(boxcarType)
        }

        @Test
        fun should_updateOnSaveCarTypeToRepository() {
            //assign
            val boxcarType: CarType = TestAARTypeCreator.boxcarType()
            val existingBoxcarType: CarType = TestAARTypeCreator.boxcarType()
            carTypeDALFake!!.setCurrentSavedEntity(existingBoxcarType)

            //act
            val returnedCarType = service!!.saveCarTypeToDatabase(boxcarType)
            //assert
            AssertionsForClassTypes.assertThat(carTypeDALFake!!.savedEntity()).isEqualTo(boxcarType)
            AssertionsForClassTypes.assertThat(returnedCarType).isEqualTo(boxcarType)
        }

        @Test
        fun should_insertOnSaveCarTypeToRepository() {
            //assign
            val boxcarType: CarType = TestAARTypeCreator.boxcarType()

            //act
            val returnedCarTypeDto = service!!.saveCarTypeToDatabase(boxcarType)
            //assert
            AssertionsForClassTypes.assertThat(carTypeDALFake!!.savedEntity().toString())
                .isEqualTo(returnedCarTypeDto.toString())
        }

        @Test
        fun should_getExistingCarTypeByAAR() {
            //assign
            val existingCarType: CarType = TestAARTypeCreator.gondolaType()
            carTypeDALFake!!.setEntityToReturn(existingCarType)
            //act
            val carTypeForAAR = service!!.carTypeForAAR(AARDesignation.GS)
            //assert
            AssertionsForClassTypes.assertThat(carTypeForAAR).isEqualTo(existingCarType)
        }

        @Test
        fun should_getNullCarType_whenNonExistent_AAR() {
            //assign
            //act
            val carTypeForAAR = service!!.carTypeForAAR(AARDesignation.GS)
            //assert
            AssertionsForClassTypes.assertThat(carTypeForAAR.isNull).isTrue
        }

        @Test
        fun should_getCarTypesThatCarry_goods() {
            //assign
            val boxcarType: CarType = TestAARTypeCreator.boxcarType()
            val gondolaCarType: CarType = TestAARTypeCreator.gondolaType()
            carTypeDALFake!!.setReturnedEntityList(Arrays.asList(boxcarType, gondolaCarType))
            //act
            val carTypesThatCarryGoods = service!!.carTypesThatCarryGoodsType(GoodsType.Parts)
            //assert
            AssertionsForClassTypes.assertThat(carTypesThatCarryGoods.size).isEqualTo(2)
            AssertionsForClassTypes.assertThat(
                carTypesThatCarryGoods.stream().anyMatch { carType: CarType -> carType === boxcarType })
            AssertionsForClassTypes.assertThat(
                carTypesThatCarryGoods.stream().anyMatch { carType: CarType -> carType === gondolaCarType })
        }
    }
}
