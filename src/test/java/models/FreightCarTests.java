package models;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static models.CarType.Boxcar;
import static models.GoodsType.Ingredients;
import static models.GoodsType.Lumber;
import static models.GoodsType.Oil;
import static models.GoodsType.Paper;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class FreightCarTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_returnListOfGoods()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);
            carriedGoodsList.add(Paper);
            final FreightCar freightCar = new FreightCar("PNWR 1234",Boxcar, carriedGoodsList);

            //act
            final boolean canCarryIngredients = freightCar.canCarry(Ingredients);
            final boolean canCarryPaper = freightCar.canCarry(Ingredients);
            final boolean canCarryLumber = freightCar.canCarry(Lumber);
            //assert
            assertThat(canCarryIngredients).isTrue();
            assertThat(canCarryPaper).isTrue();
            assertThat(canCarryLumber).isFalse();
        }

        @Test
        public void should_returnLoadFreightCar() throws Exception
        {
            //assign
            final ArrayList<GoodsType> goods = new ArrayList<>();
            goods.add(Ingredients);
            final FreightCar freightCar = new FreightCar("PNWR 1234", Boxcar, goods);

            //act
            assertThat(freightCar.isLoaded()).isFalse();
            freightCar.load(Ingredients);
            //assert
            assertThat(freightCar.isLoaded()).isTrue();
        }

        @Test
        public void should_ThrowWhenTryingToLoadGoodsThat_cantHold() throws Exception
        {
            //assign
            final ArrayList<GoodsType> goods = new ArrayList<>();
            goods.add(Ingredients);
            final FreightCar freightCar = new FreightCar("PNWR 1234", Boxcar, goods);

            //act
            //assert
            assertThatThrownBy(() ->
            {
                freightCar.load(Oil);
            }).hasMessage("This car cannot carry Oil");
        }

        @Test
        public void should_returnDisplayName_asType_andRoadName()
        {
            //assign
            final ArrayList<GoodsType> goods = new ArrayList<>();
            goods.add(Ingredients);
            final FreightCar freightCar = new FreightCar("PNWR 1234", Boxcar, goods);

            //act
            final String displayName = freightCar.displayName();
            //assert
            assertThat(displayName).isEqualTo("Boxcar - PNWR 1234");
        }
    }
}