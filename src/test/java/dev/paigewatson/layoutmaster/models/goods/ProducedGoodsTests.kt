package dev.paigewatson.layoutmaster.models.goods

import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class ProducedGoodsTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        @Test
        fun should_haveGoodsType_andNotBeAssigned() {
            //assign
            val producedGood = ProducedGood(GoodsType.Paper)

            //act

            //assert
            AssertionsForClassTypes.assertThat(producedGood.isOfType(GoodsType.Paper)).isTrue
            AssertionsForClassTypes.assertThat(producedGood.isOfType(GoodsType.Parts)).isFalse
            AssertionsForClassTypes.assertThat(producedGood.isAssigned).isFalse
        }

        @Test
        fun should_representItselfAsString() {
            //assign
            val producedGood = ProducedGood(GoodsType.Paper)

            //act
            //assert
            AssertionsForClassTypes.assertThat(producedGood.toString())
                .isEqualTo("ProducedGood{goodsType=Paper, isAssigned=false}")
        }
    }
}
