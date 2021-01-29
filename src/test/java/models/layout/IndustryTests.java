package models.layout;

import models.goods.ProducedGood;
import models.goods.RequiredGood;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static models.goods.GoodsType.Chemicals;
import static models.goods.GoodsType.MetalParts;
import static models.goods.GoodsType.Oil;
import static models.goods.GoodsType.Parts;
import static models.goods.GoodsType.ScrapMetal;
import static models.goods.GoodsType.SheetMetal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IndustryTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_haveName_andListOfAcceptedGoods()
        {
            //assign
            final ArrayList<RequiredGood> acceptedGoodsList = new ArrayList<>();
            acceptedGoodsList.add(new RequiredGood(Chemicals));
            acceptedGoodsList.add(new RequiredGood(Parts));
            acceptedGoodsList.add(new RequiredGood(SheetMetal, true));

            final ArrayList<ProducedGood> returnedGoodsList = new ArrayList<>();
            returnedGoodsList.add(new ProducedGood(MetalParts));
            returnedGoodsList.add(new ProducedGood(ScrapMetal));

            final Industry cogFactory = new Industry("Cog Factory", acceptedGoodsList, returnedGoodsList);

            //act
            //assert
            assertThat(cogFactory.needs(Parts)).isTrue();
            assertThat(cogFactory.needs(SheetMetal)).isFalse();
            assertThat(cogFactory.needs(MetalParts)).isFalse();
            assertThat(cogFactory.produces(ScrapMetal)).isTrue();
            assertThat(cogFactory.produces(Oil)).isFalse();
            assertThat(cogFactory.name()).isEqualTo("Cog Factory");
        }
    }
}