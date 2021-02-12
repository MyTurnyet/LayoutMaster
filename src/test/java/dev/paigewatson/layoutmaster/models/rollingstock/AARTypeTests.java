package dev.paigewatson.layoutmaster.models.rollingstock;

import dev.paigewatson.layoutmaster.helpers.CarTypeDALFake;
import dev.paigewatson.layoutmaster.helpers.EntityCreator;
import dev.paigewatson.layoutmaster.models.data.CarTypeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
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
        private UUID boxcarUUID;
        private CarType boxcarType;

        @BeforeEach
        public void setup()
        {
            boxcarUUID = UUID.randomUUID();
            boxcarType = EntityCreator.boxcarType(boxcarUUID);
        }

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

            //act
            //assert
            assertThat(boxcarType.canCarry(Ingredients)).isTrue();
            assertThat(boxcarType.canCarry(ScrapMetal)).isFalse();
            assertThat(boxcarType.isOfType(XM)).isTrue();
            assertThat(boxcarType.isOfType(FA)).isFalse();
            assertThat(boxcarType.displayName()).isEqualTo(XM);
        }

        @Test
        public void should_saveItselfToRepository()
        {
            //assign
            final CarTypeDALFake carTypeDALFake = new CarTypeDALFake();
            carTypeDALFake.setEntityToReturn(new NullCarType());
            //act
            final CarType savedCarType = boxcarType.saveToDatabase(carTypeDALFake);

            assertThat(carTypeDALFake.savedEntity()).isEqualTo(boxcarType);
            assertThat(savedCarType).isEqualTo(boxcarType);
        }

        @Test
        public void should_updateItselfToRepository_ifAARType_exists()
        {
            //assign
            final CarTypeDALFake carTypeDALFake = new CarTypeDALFake();
            carTypeDALFake.setEntityToReturn(boxcarType);
            UUID boxcarUUID2 = UUID.randomUUID();
            CarType boxcarType2 = new AARType(boxcarUUID2, XM, Arrays.asList(Ingredients, Paper));

            //act
            final CarType savedCarType = boxcarType2.saveToDatabase(carTypeDALFake);

            assertThat(carTypeDALFake.savedEntity()).isEqualTo(boxcarType2);
            assertThat(savedCarType).isEqualTo(boxcarType2);
        }

        @Test
        public void should_convertToDTO()
        {
            //assign
            //act
            final CarTypeDto boxcarTypeDto = boxcarType.getDto();
            //assert
            assertThat(boxcarTypeDto.toString()).isEqualTo("AARTypeDto{id='" +
                    boxcarUUID.toString() +
                    "', aarType='XM', carriedGoods=[Ingredients, Logs, Parts]}");
        }
    }
}