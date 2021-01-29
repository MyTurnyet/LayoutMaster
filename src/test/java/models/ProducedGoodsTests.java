package models;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static models.GoodsType.Paper;
import static models.GoodsType.Parts;
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

        @Test
        public void should_beAssigned_inConstructor()
        {
            //assign
            final DeliverableGood producedGood = new ProducedGood(Paper, true);

            //act
            //assert
            assertThat(producedGood.isAssigned()).isTrue();
        }

        @Test
        public void should_beAssigned_whenMarkAsAssignedToDeliver()
        {
            //assign
            final DeliverableGood requiredGood = new ProducedGood(Parts);
            //act
            requiredGood.markAssignedToDeliver();
            //assert
            assertThat(requiredGood.isAssigned()).isTrue();
        }

    }
}