package dev.paigewatson.layoutmaster.models.data;

import dev.paigewatson.layoutmaster.models.rollingstock.CarType;
import dev.paigewatson.layoutmaster.models.rollingstock.NullCarType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Ingredients;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Paper;
import static dev.paigewatson.layoutmaster.models.goods.GoodsType.Parts;
import static dev.paigewatson.layoutmaster.models.rollingstock.AARDesignation.XM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarTypeDtoTests
{
    @Nested
    @Tag("Unit")
    class NullCarTypeDtoTests
    {
        @Test
        public void should_createNullCarTypeDto()
        {
            //assign
            final NullCarTypeDto nullCarTypeDto = new NullCarTypeDto();

            //assert
            assertThat(nullCarTypeDto.isNull()).isTrue();
        }

        @Test
        public void should_convertToNullCarType()
        {
            //assign
            final NullCarTypeDto nullCarTypeDto = new NullCarTypeDto();

            //act
            NullCarType nullCarType = nullCarTypeDto.getEntity();
            //assert
            assertThat(nullCarType.isNull()).isTrue();
        }
    }

    @Nested
    @Tag("Unit")
    class AARTypeDtoTests
    {
        @Test
        public void should_createCarTypeDto()
        {
            //assign
            final List<String> carriedGoods = Arrays.asList("Ingredients", "Paper", "Parts");
            final String expectedAARType = "XM";

            final AARTypeDto carTypeDto = new AARTypeDto(expectedAARType, carriedGoods);

            //assert
            assertThat(carTypeDto.isNull()).isFalse();
            assertThat(carTypeDto.carriedGoods).isEqualTo(carriedGoods);
            assertThat(carTypeDto.aarType).isEqualTo(expectedAARType);
        }

        @Test
        public void should_CreateEntityFromDTO()
        {
            //assign
            final List<String> carriedGoods = Arrays.asList("Ingredients", "Paper", "Parts");
            final String expectedAARType = "XM";
            final UUID uuid = UUID.randomUUID();
            final AARTypeDto carTypeDto = new AARTypeDto(uuid, expectedAARType, carriedGoods);

            //act
            final CarType aarType = carTypeDto.getEntity();
            //assert
            assertThat(aarType.isOfType(XM)).isTrue();
            assertThat(aarType.canCarry(Ingredients)).isTrue();
            assertThat(aarType.canCarry(Paper)).isTrue();
            assertThat(aarType.canCarry(Parts)).isTrue();
        }
    }
}