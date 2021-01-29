package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Lumber;
import static dev.paigewatson.layoutmaster.models.rollingstock.CarTypeDesignation.Boxcar;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FreightCarTests
{
    public static FreightCar createTestFreightCar()
    {
        final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
        carriedGoodsList.add(Ingredients);

        final CarType boxCarType = new CarType(Boxcar, carriedGoodsList);
        return new FreightCar("PNWR", 1234, boxCarType);
    }

    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_returnListOfGoods()
        {
            //assign
            final FreightCar freightCar = createTestFreightCar();

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
        public void should_LoadFreightCar()
        {
            //assign
            final FreightCar freightCar = createTestFreightCar();

            //act
            assertThat(freightCar.isLoaded()).isFalse();
            freightCar.load(Ingredients);
            //assert
            assertThat(freightCar.isLoaded()).isTrue();
        }

        @Test
        public void should_returnDisplayName_asType_andRoadName()
        {
            //assign
            final FreightCar freightCar = createTestFreightCar();

            //act
            final String displayName = freightCar.displayName();
            //assert
            assertThat(displayName).isEqualTo("Boxcar - PNWR 1234");
        }

        @Test
        public void should_produceItselfAsString()
        {
            //assign
            final FreightCar freightCar = createTestFreightCar();

            //act
            final String freightCarAsString = freightCar.toString();
            //assert
            assertThat(freightCarAsString)
                    .isEqualTo(
                            "FreightCar{roadName='PNWR', roadNumber=1234, " +
                                    "carType=CarType{id='null', carTypeDesignation=Boxcar, carriedGoodsList=[Ingredients]}," +
                                    " currentlyCarriedGoods=EMPTY}");
        }

    }
}