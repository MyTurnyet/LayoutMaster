package dev.paigewatson.layoutmaster.models.goods

import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class RequiredGoodTests {
    @Nested
    @Tag("Unit")
    internal inner class UnitTests {
        @Test
        fun should_haveGoodsType_andReturnNeedsTrue_ifMatchedType_andNotAssigned() {
            //assign
            val requiredGood = RequiredGood(GoodsType.Parts)

            //act
            //assert
            AssertionsForClassTypes.assertThat(requiredGood.needs(GoodsType.Parts)).isTrue
        }

        @Test
        fun should_returnNeeds_asFalseIfAssigned_orNotMatchingType() {
            //assign
            //act
            val requiredGood = RequiredGood(GoodsType.Parts, true)

            //assert
            AssertionsForClassTypes.assertThat(requiredGood.needs(GoodsType.Parts)).isFalse
            AssertionsForClassTypes.assertThat(requiredGood.needs(GoodsType.Oil)).isFalse
        }

        @Test
        fun should_representItselfAsString() {
            //assign
            val requiredGood = RequiredGood(GoodsType.Parts)

            //act
            //assert
            AssertionsForClassTypes.assertThat(requiredGood.toString())
                .isEqualTo("RequiredGood{neededGoodsType=Parts, isAssigned=false}")
        }
    }
}
