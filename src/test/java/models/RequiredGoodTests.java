package models;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static models.GoodsType.Oil;
import static models.GoodsType.Parts;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RequiredGoodTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_haveGoodsType_andNotBeAssigned()
        {
            //assign
            final RequiredGood requiredGood = new RequiredGood(Parts);

            //act
            //assert
            assertThat(requiredGood.needs(Parts)).isTrue();
            assertThat(requiredGood.needs(Oil)).isFalse();
            assertThat(requiredGood.isAssigned()).isFalse();
        }

        @Test
        public void should_beAssigned_fromConstructor()
        {
            //assign
            //act
            final RequiredGood requiredGood = new RequiredGood(Parts, true);

            //assert
            assertThat(requiredGood.isAssigned()).isTrue();
        }

        @Test
        public void should_beAssigned_whenMarkAsAssignedToDeliver()
        {
            //assign
            final RequiredGood requiredGood = new RequiredGood(Parts);
            //act
            requiredGood.markAssignedToDeliver();
            //assert
            assertThat(requiredGood.isAssigned()).isTrue();
        }
    }
}