package dev.paigewatson.layoutmaster.models.goods;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Oil;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Parts;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RequiredGoodTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_haveGoodsType_andReturnNeedsTrue_ifMatchedType_andNotAssigned()
        {
            //assign
            final RequiredGood requiredGood = new RequiredGood(Parts);

            //act
            //assert
            assertThat(requiredGood.needs(Parts)).isTrue();
        }

        @Test
        public void should_returnNeeds_asFalseIfAssigned_orNotMatchingType()
        {
            //assign
            //act
            final RequiredGood requiredGood = new RequiredGood(Parts, true);

            //assert
            assertThat(requiredGood.needs(Parts)).isFalse();
            assertThat(requiredGood.needs(Oil)).isFalse();
        }

        @Test
        public void should_representItselfAsString()
        {
            //assign
            final RequiredGood requiredGood = new RequiredGood(Parts);

            //act
            //assert
            assertThat(requiredGood.toString()).isEqualTo("RequiredGood{neededGoodsType=Parts, isAssigned=false}");
        }
    }
}