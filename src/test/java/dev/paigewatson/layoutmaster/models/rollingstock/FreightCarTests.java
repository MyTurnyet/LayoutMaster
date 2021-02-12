package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.helpers.TestAARTypeCreator;
import dev.paigewatson.layoutmaster.models.data.FreightCarDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Lumber;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.GS;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FreightCarTests
{
    public static FreightCar createTestFreightCar()
    {
        return createTestFreightCar(UUID.randomUUID(), UUID.randomUUID());
    }

    public static FreightCar createTestFreightCar(UUID freightCarUUID, UUID carTypeUUID)
    {
        final AARType boxCarType = TestAARTypeCreator.boxcarType(carTypeUUID);
        return new FreightCar(freightCarUUID, "PNWR", 1234, boxCarType);
    }

    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_knowWhatGoodsItCanCarry()
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
            final RollingStock freightCar = createTestFreightCar();

            //act
            freightCar.load(Ingredients);
            //assert
            assertThat(freightCar.isCarrying(Ingredients)).isTrue();
        }

        @Test
        public void should_returnDisplayName_asType_andRoadName()
        {
            //assign
            final FreightCar freightCar = createTestFreightCar();

            //act
            final String displayName = freightCar.displayName();
            //assert
            assertThat(displayName).isEqualTo("XM - PNWR 1234");
        }

        @Test
        public void should_beOfTypeXM_andNotNull()
        {
            //assign
            final RollingStock freightCar = createTestFreightCar();

            //act
            //assert
            assertThat(freightCar.isAARType(XM)).isTrue();
            assertThat(freightCar.isAARType(GS)).isFalse();
            assertThat(freightCar.isNull()).isFalse();
        }

        @Test
        public void should_returnRollingStockDto()
        {
            //assign
            final UUID freightCarUUID = UUID.randomUUID();
            final UUID aarTypeUUID = UUID.randomUUID();
            final FreightCar freightCar = createTestFreightCar(freightCarUUID, aarTypeUUID);

            //act
            final FreightCarDto freightCarDto = freightCar.getDto();

            //assert
            assertThat(freightCarDto.toString()).isEqualTo("FreightCarDto{id='" +
                    freightCarUUID.toString() +
                    "', roadName='PNWR', roadNumber=1234, " +
                    "carTypeDto=AARTypeDto{id='" +
                    aarTypeUUID.toString() +
                    "', aarType='XM', carriedGoods=[Ingredients, Logs, Parts]}}");
        }
    }
}