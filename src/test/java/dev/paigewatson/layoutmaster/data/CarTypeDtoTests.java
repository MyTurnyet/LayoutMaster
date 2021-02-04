package dev.paigewatson.layoutmaster.data;

import dev.paigewatson.layoutmaster.data.models.CarTypeDto;
import dev.paigewatson.layoutmaster.data.models.NullCarTypeDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            assertThat(nullCarTypeDto.id).isEqualTo("");
            assertThat(nullCarTypeDto.carriedGoods).isEqualTo(Collections.emptyList());
            assertThat(nullCarTypeDto.aarType).isEqualTo("");
        }
    }

    @Nested
    @Tag("Unit")
    class NonNullCarTypeDtoTests
    {
        @Test
        public void should_createCarTypeDto()
        {
            //assign
            final List<String> carriedGoods = Arrays.asList("Ingredients", "Paper", "Parts");
            final String expectedAARType = "XM";
            final String expectedId = "FOO!";
            final CarTypeDto carTypeDto = new CarTypeDto(expectedId, expectedAARType, carriedGoods);

            //assert
            assertThat(carTypeDto.isNull()).isFalse();
            assertThat(carTypeDto.id).isEqualTo(expectedId);
            assertThat(carTypeDto.carriedGoods).isEqualTo(carriedGoods);
            assertThat(carTypeDto.aarType).isEqualTo(expectedAARType);
        }
    }
}