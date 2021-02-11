package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.helpers.CarTypeDALFake;
import dev.paigewatson.layoutmaster.models.data.CarTypeDto;
import dev.paigewatson.layoutmaster.models.goods.GoodsType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Logs;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Paper;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.ScrapMetal;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.FA;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.NULL;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AARTypeTests
{
    @Nested
    @Tag("Unit")
    class UnitTests
    {
        @Test
        public void should_evaluateTo_NullCarType()
        {
            //assign
            CarType nullCarType = new NullCarType();
            //act
            //assert
            final boolean ofType = nullCarType.isOfType(XM);
            final boolean canCarry = nullCarType.canCarry(Ingredients);
            final AARDesignation displayName = nullCarType.displayName();
            final boolean isNull = nullCarType.isNull();

            assertThat(isNull).isTrue();
            assertThat(displayName).isEqualTo(NULL);
            assertThat(canCarry).isFalse();
            assertThat(ofType).isFalse();

        }

        @Test
        public void should_haveTypeName_andGoodsCarried()
        {
            //assign
            final ArrayList<GoodsType> carriedGoodsList = new ArrayList<>();
            carriedGoodsList.add(Ingredients);

            final CarType boxCarType = new AARType(XM, carriedGoodsList);

            //act
            //assert
            assertThat(boxCarType.canCarry(Ingredients)).isTrue();
            assertThat(boxCarType.canCarry(ScrapMetal)).isFalse();
            assertThat(boxCarType.isOfType(XM)).isTrue();
            assertThat(boxCarType.isOfType(FA)).isFalse();
            assertThat(boxCarType.displayName()).isEqualTo(XM);
        }

        @Test
        public void should_saveItselfToRepository()
        {
            //assign
            final CarTypeDALFake carTypeDALFake = new CarTypeDALFake();
            UUID boxcarUUID = UUID.randomUUID();
            CarType boxcarType = new AARType(boxcarUUID, XM, Arrays.asList(Ingredients, Logs));
            carTypeDALFake.setEntityToReturn(new NullCarType());
            //act
            final CarType savedCarType = boxcarType.saveToDatabase(carTypeDALFake);

            assertThat(carTypeDALFake.savedEntity()).isEqualTo(boxcarType);
        }

        @Test
        public void should_updateItselfToRepository_ifAARType_exists()
        {
            //assign
            final CarTypeDALFake carTypeDALFake = new CarTypeDALFake();
            UUID boxcarUUID = UUID.randomUUID();
            CarType boxcarType = new AARType(boxcarUUID, XM, Arrays.asList(Ingredients, Logs));
            carTypeDALFake.setEntityToReturn(boxcarType);
            UUID boxcarUUID2 = UUID.randomUUID();
            CarType boxcarType2 = new AARType(boxcarUUID2, XM, Arrays.asList(Ingredients, Paper));

            //act
            final CarType savedCarType = boxcarType.saveToDatabase(carTypeDALFake);

            assertThat(carTypeDALFake.savedEntity()).isEqualTo(boxcarType);
        }

        @Test
        public void should_convertToDTO()
        {
            //assign
            UUID boxcarUUID = UUID.randomUUID();
            CarType boxcarType = new AARType(boxcarUUID, XM, Arrays.asList(Ingredients, Logs));

            //act
            final CarTypeDto boxcarTypeDto = boxcarType.getDto();
            //assert
            assertThat(boxcarTypeDto.toString()).isEqualTo("AARTypeDto{id='" +
                    boxcarUUID.toString() +
                    "', aarType='XM', carriedGoods=[Ingredients, Logs]}");
        }
    }
}