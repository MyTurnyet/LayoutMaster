package models.layout;

import models.goods.GoodsType;
import models.rollingstock.FreightCar;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static models.goods.GoodsType.Ingredients;
import static models.goods.GoodsType.Paper;
import static models.rollingstock.CarType.Boxcar;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IndustryTrackTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_haveName_AndHasOpenSlots()
        {
            //assign
            final IndustryTrack industryTrack = new IndustryTrack("factory siding", 1);

            //act
            //assert
            assertThat(industryTrack.hasOpenSlots()).isTrue();
            assertThat(industryTrack.name()).isEqualTo("factory siding");
        }

        @Test
        public void should_haveName_AndHasNoOpenSlots()
        {
            //assign
            final IndustryTrack industryTrack = new IndustryTrack("factory siding", 1);
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);
            carriedGoodsList.add(Paper);
            final FreightCar freightCar = new FreightCar("PNWR 1234", Boxcar, carriedGoodsList);
            //act
            industryTrack.setOutCar(freightCar);
            //assert
            assertThat(industryTrack.hasOpenSlots()).isFalse();
        }
    }
}