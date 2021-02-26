package dev.paigewatson.layoutmaster.models.rollingstock

import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator.boxcarType
import dev.paigewatson.layoutmaster.models.goods.GoodsType
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.util.*

object FreightCarTests {
    @JvmOverloads
    fun createTestFreightCar(
        freightCarUUID: UUID? = UUID.randomUUID(),
        carTypeUUID: UUID? = UUID.randomUUID()
    ): FreightCar {
        val boxCarType = boxcarType(carTypeUUID)
        return FreightCar(freightCarUUID!!, "PNWR", 1234, boxCarType)
    }

    @Nested
    @Tag("Unit")
    internal class UnitTests {
        @Test
        fun should_knowWhatGoodsItCanCarry() {
            //assign
            val freightCar = createTestFreightCar()

            //act
            val canCarryIngredients = freightCar.canCarry(GoodsType.Ingredients)
            val canCarryPaper = freightCar.canCarry(GoodsType.Ingredients)
            val canCarryLumber = freightCar.canCarry(GoodsType.Lumber)
            //assert
            AssertionsForClassTypes.assertThat(canCarryIngredients).isTrue
            AssertionsForClassTypes.assertThat(canCarryPaper).isTrue
            AssertionsForClassTypes.assertThat(canCarryLumber).isFalse
        }

        @Test
        fun should_LoadFreightCar() {
            //assign
            val freightCar: RollingStock = createTestFreightCar()

            //act
            freightCar.load(GoodsType.Ingredients)
            //assert
            AssertionsForClassTypes.assertThat(freightCar.isCarrying(GoodsType.Ingredients)).isTrue
        }

        @Test
        fun should_returnDisplayName_asType_andRoadName() {
            //assign
            val freightCar = createTestFreightCar()

            //act
            val displayName = freightCar.displayName()
            //assert
            AssertionsForClassTypes.assertThat(displayName).isEqualTo("XM - PNWR 1234")
        }

        @Test
        fun should_beOfTypeXM_andNotNull() {
            //assign
            val freightCar: RollingStock = createTestFreightCar()

            //act
            //assert
            AssertionsForClassTypes.assertThat(freightCar.isAARType(AARDesignation.XM)).isTrue
            AssertionsForClassTypes.assertThat(freightCar.isAARType(AARDesignation.GS)).isFalse
            AssertionsForClassTypes.assertThat(freightCar.isNull).isFalse
        }
    }
}
