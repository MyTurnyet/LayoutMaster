package dev.paigewatson.layoutmaster.models.rollingstock

import dev.paigewatson.layoutmaster.helpers.CarTypeDALFake
import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator.boxcarType
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.util.*

class AARTypeTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        private var boxcarUUID: UUID = UUID.randomUUID()
        private var boxcarType: CarType = boxcarType(boxcarUUID)

        @Test
        fun should_evaluateTo_NullCarType() {
            //assign
            val nullCarType: CarType = NullCarType()
            //act
            //assert
            val ofType = nullCarType.isOfType(AARDesignation.XM)
            val canCarry = nullCarType.canCarry(GoodsType.Ingredients)
            val displayName = nullCarType.displayName()
            val isNull = nullCarType.isNull
            AssertionsForClassTypes.assertThat(isNull).isTrue
            AssertionsForClassTypes.assertThat(displayName).isEqualTo(AARDesignation.NULL)
            AssertionsForClassTypes.assertThat(canCarry).isFalse
            AssertionsForClassTypes.assertThat(ofType).isFalse
        }

        @Test
        fun should_haveTypeName_andGoodsCarried() {
            //assign

            //act
            //assert
            AssertionsForClassTypes.assertThat(boxcarType.canCarry(GoodsType.Ingredients)).isTrue
            AssertionsForClassTypes.assertThat(boxcarType.canCarry(GoodsType.ScrapMetal)).isFalse
            AssertionsForClassTypes.assertThat(boxcarType.isOfType(AARDesignation.XM)).isTrue
            AssertionsForClassTypes.assertThat(boxcarType.isOfType(AARDesignation.FA)).isFalse
            AssertionsForClassTypes.assertThat(boxcarType.displayName()).isEqualTo(AARDesignation.XM)
        }

        @Test
        fun should_saveItselfToRepository() {
            //assign
            val carTypeDALFake = CarTypeDALFake()
            carTypeDALFake.setEntityToReturn(NullCarType())
            //act
            val savedCarType = boxcarType.saveToDatabase(carTypeDALFake)
            AssertionsForClassTypes.assertThat(carTypeDALFake.savedEntity()).isEqualTo(boxcarType)
            AssertionsForClassTypes.assertThat(savedCarType).isEqualTo(boxcarType)
        }

        @Test
        fun should_updateItselfToRepository_ifAARType_exists() {
            //assign
            val carTypeDALFake = CarTypeDALFake()
            carTypeDALFake.setEntityToReturn(boxcarType)
            val boxcarUUID2 = UUID.randomUUID()
            val boxcarType2: CarType =
                AARType(boxcarUUID2, AARDesignation.XM, listOfNotNull(GoodsType.Ingredients, GoodsType.Paper))

            //act
            val savedCarType = boxcarType2.saveToDatabase(carTypeDALFake)
            AssertionsForClassTypes.assertThat(carTypeDALFake.savedEntity()).isEqualTo(boxcarType2)
            AssertionsForClassTypes.assertThat(savedCarType).isEqualTo(boxcarType2)
        }
    }
}
