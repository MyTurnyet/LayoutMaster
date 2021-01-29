package models.layout;

import models.goods.GoodsType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static models.goods.GoodsType.Chemicals;
import static models.goods.GoodsType.MetalParts;
import static models.goods.GoodsType.MetalScraps;
import static models.goods.GoodsType.Parts;
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
            final ArrayList<GoodsType> acceptedGoodsList = new ArrayList<>();
            acceptedGoodsList.add(Chemicals);
            acceptedGoodsList.add(Parts);
            acceptedGoodsList.add(SheetMetal);

            final ArrayList<GoodsType> returnedGoodsList = new ArrayList<>();
            returnedGoodsList.add(MetalParts);
            returnedGoodsList.add(MetalScraps);

            final Industry cogFactory = new Industry("Cog Factory", acceptedGoodsList, returnedGoodsList);

            //act
            //assert
            assertThat(cogFactory.needs(Parts)).isTrue();
            assertThat(cogFactory.needs(MetalParts)).isFalse();
            assertThat(cogFactory.Name()).isEqualTo("Cog Factory");
        }
    }
}