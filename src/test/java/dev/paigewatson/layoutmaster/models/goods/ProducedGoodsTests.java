package dev.paigewatson.layoutmaster.models.goods;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Paper;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Parts;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProducedGoodsTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_haveGoodsType_andNotBeAssigned()
        {
            //assign
            final ProducedGood producedGood = new ProducedGood(Paper);

            //act

            //assert
            assertThat(producedGood.isOfType(Paper)).isTrue();
            assertThat(producedGood.isOfType(Parts)).isFalse();
            assertThat(producedGood.isAssigned()).isFalse();
        }
    }
}