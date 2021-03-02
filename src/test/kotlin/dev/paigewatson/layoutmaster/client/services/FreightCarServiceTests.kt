package dev.paigewatson.layoutmaster.client.services

import dev.paigewatson.layoutmaster.helpers.RollingStockDALFake
import dev.paigewatson.layoutmaster.helpers.TestFreightCarCreator
import dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation
import dev.paigewatson.layoutmaster.models.rollingstock.RollingStock
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class FreightCarServiceTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        private var freightCarService: FreightCarService? = null
        private var rollingStockDALFake: RollingStockDALFake? = null
        private var boxcarOne: RollingStock? = null
        private var boxcarTwo: RollingStock? = null
        private var boxcarThree: RollingStock? = null
        private var gondolaOne: RollingStock? = null
        private var gondolaTwo: RollingStock? = null
        private var gondolaThree: RollingStock? = null
        private var flatCarOne: RollingStock? = null

        @BeforeEach
        fun setupTests() {
            rollingStockDALFake = RollingStockDALFake()
            boxcarOne = TestFreightCarCreator.boxcar("PNWR", 2341)
            boxcarTwo = TestFreightCarCreator.boxcar("BCR", 2342)
            boxcarThree = TestFreightCarCreator.boxcar("PNWR", 2335)
            flatCarOne = TestFreightCarCreator.flatcar("ATSF", 1232)
            gondolaOne = TestFreightCarCreator.gondola("BNSF", 1234)
            gondolaTwo = TestFreightCarCreator.gondola("PNWR", 1235)
            gondolaThree = TestFreightCarCreator.gondola("BCR", 1237)
            freightCarService = MongoFreightCarService(rollingStockDALFake!!)
        }

        @Test
        fun should_getAllFreightCars_fromRepository() {
            //assign
            rollingStockDALFake!!.setReturnedValuesList(
                listOfNotNull(
                    boxcarOne, boxcarTwo, boxcarThree,
                    gondolaOne, flatCarOne, gondolaTwo, gondolaThree
                )
            )
            //act
            val rollingStockList = freightCarService!!.allFreightCars()
            //assert
            AssertionsForClassTypes.assertThat(rollingStockList.size).isEqualTo(7)
        }

        @Test
        fun should_getAllFreightCars_fromRepository_matchingAARType_XM() {
            //assign
            rollingStockDALFake!!.setReturnedValuesList(
                listOfNotNull(
                    boxcarOne, boxcarTwo, boxcarThree
                )
            )

            //act
            val rollingStockList = freightCarService!!.allFreightCarsByAARType(AARDesignation.XM)
            //assert
            AssertionsForClassTypes.assertThat(rollingStockList.size).isEqualTo(3)
        }

        @Test
        fun should_getAllFreightCars_fromRepository_withBCR_roadName() {
            //assign
            rollingStockDALFake!!.setReturnedValuesList(
                listOfNotNull(
                    boxcarTwo, gondolaThree
                )
            )

            //act
            val rollingStockList = freightCarService!!.allFreightCarsByRoadName("BCR")
            //assert
            AssertionsForClassTypes.assertThat(rollingStockList.size).isEqualTo(2)
        }

        @Test
        fun should_insertNewFreightCar_IntoRepository() {
            //act
            val savedRollingStock = freightCarService!!.saveFreightCarToDatabase(boxcarThree!!)
            //assert
            AssertionsForClassTypes.assertThat(rollingStockDALFake!!.savedEntity).isEqualTo(boxcarThree)
            AssertionsForClassTypes.assertThat(savedRollingStock).isEqualTo(boxcarThree)
        }

        @Test
        fun should_deleteFreightCar_fromDatabase() {
            //assign
            freightCarService!!.delete(boxcarOne!!)

            //assert
            AssertionsForClassTypes.assertThat(rollingStockDALFake!!.deletedEntity).isEqualTo(boxcarOne)
        }
    }
}
