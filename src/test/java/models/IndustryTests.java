package models;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static models.GoodsType.Chemicals;
import static models.GoodsType.MetalParts;
import static models.GoodsType.Parts;
import static models.GoodsType.SheetMetal;
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
            returnedGoodsList.add(SheetMetal);

            final Industry cogFactory = new Industry("Cog Factory", acceptedGoodsList, returnedGoodsList);

            //act

            //assert

        }
    }
}